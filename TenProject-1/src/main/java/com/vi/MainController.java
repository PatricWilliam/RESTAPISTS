package com.vi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vi.controller.CartController;
import com.vi.controller.CourseController;
import com.vi.controller.CustomerController;
import com.vi.controller.OrderController;
import com.vi.exception.DataValidationException;
import com.vi.exception.ErrorDetails;
import com.vi.exception.ResourceNotFoundException;
import com.vi.exception.UnauthorisedException;
import com.vi.model.Cart;
import com.vi.model.Course;
import com.vi.model.CustomerDetails;
import com.vi.model.Order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class MainController {

	@Autowired
	CustomerController cusCon;
	@Autowired
	CourseController courseCon;
	@Autowired
	OrderController orderCon;
	@Autowired
	CartController cartCon;

	@Operation(summary = "Create Customer")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "customer created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDetails.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "500", description = "internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }) })
	@PostMapping(path = "/customer/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createCustomerService(@Validated @RequestBody CustomerDetails customer,
			Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(400).body("error occured");
		}
		if (customer.getUserName().isEmpty()) {
			throw new DataValidationException("username is mandatory");
		}
		return ResponseEntity.status(201).body(cusCon.createCustomer(customer));
	}

	@Operation(summary = "Course Create")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Course created", content = {
			@Content(mediaType = "application/json", array = @ArraySchema(arraySchema = @Schema(implementation = Course.class))) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "500", description = "internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }) })
	@PostMapping(path = "/course/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createCourseService(@Validated @RequestBody List<Course> course, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(400).body("error occured");
		}
		if (courseCon.findAllCourseByCourseId(course)) {
			return ResponseEntity.status(400).body("Course Already exist");
		}
		return ResponseEntity.status(201).body(courseCon.createCourse(course));
	}

	@Operation(summary = "Get All Courses")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Courses Retrived", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)) }),
			@ApiResponse(responseCode = "404", description = "No Course Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "500", description = "internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }) })
	@GetMapping(path = "/course", produces = "application/json")
	public ResponseEntity<Object> getAllCourses() {
		if (courseCon.getAllCourse().isEmpty()) {
			throw new ResourceNotFoundException("No course Exist");
		}
		return ResponseEntity.status(201).body(courseCon.getAllCourse());
	}

	@Operation(summary = "Get customer by username and password")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Customer details retrived", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDetails.class)) }),
			@ApiResponse(responseCode = "404", description = "No Customer Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorised", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "500", description = "internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }) })
	@PostMapping(path = "/customer/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> getCustomerByCredentials(@Validated @RequestBody CustomerDetails customer,
			Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(400).body("error occured");
		}
		CustomerDetails customerDetails = cusCon.customerLogin(customer);
		if (customerDetails == null) {
			throw new ResourceNotFoundException("No such customer exist");
		}
		if (customerDetails.getPassword().equals(customer.getPassword())) {
			return ResponseEntity.status(200).body(customerDetails);
		} else {
			throw new UnauthorisedException("invalid combination of username & Password! Please retry");
		}
	}

	@Operation(summary = "Create Order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Order Accepted", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
			@ApiResponse(responseCode = "404", description = "No Customer Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "500", description = "internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }) })
	@PostMapping(path = "/order/{customerId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createorder(@Validated @RequestBody Order order, @PathVariable int customerId,
			Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(400).body("error occured");
		}
		if (cusCon.getCustomerId(customerId).isPresent()) {
			order.setCustomerDetails(cusCon.getCustomerId(customerId).get());
			return ResponseEntity.status(201).body(orderCon.createOrder(order));
		} else {
			throw new ResourceNotFoundException("No such customer exist");
		}
	}

	@Operation(summary = "Add Course to Cart")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Courses Added", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }),
			@ApiResponse(responseCode = "404", description = "No Cart Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "404", description = "No Course Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "500", description = "internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }) })
	@PutMapping(path = "/cart/{cartId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addtoCart(@Validated @RequestBody Cart cart, @PathVariable int cartId, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(400).body("error occured");
		}
		if (cartCon.cartAvailbale(cartId)) {
			return ResponseEntity.status(202).body(cartCon.addToCart(cart, cartId));
		}
		throw new ResourceNotFoundException("No cart found");
	}

	@Operation(summary = "Remove Course to Cart")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Courses removed", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cart.class)) }),
			@ApiResponse(responseCode = "404", description = "No Cart Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "404", description = "No Course Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "500", description = "internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }) })
	@PutMapping(path = "/cart/remove/{cartId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> removeFromCart(@Validated @RequestBody Course course, @PathVariable int cartId,
			Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(400).body("error occured");
		}
		if (cartCon.cartAvailbale(cartId)) {
			return ResponseEntity.status(202).body(cartCon.removeFromCart(course, cartId));
		}
		throw new ResourceNotFoundException("No  cart found");
	}

	@Operation(summary = "Update Customer")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Customer Details updated", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDetails.class)) }),
			@ApiResponse(responseCode = "404", description = "No Customer Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "500", description = "internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }) })
	@PutMapping(path = "customer/update/{customerId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateCustomer(@Validated @RequestBody CustomerDetails customer,
			@PathVariable int customerId, Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(400).body("error occured");
		}
		if (cusCon.getCustomerId(customerId).isPresent()) {
			return ResponseEntity.status(202).body(cusCon.updateCustomer(customer, customerId));
		}
		throw new ResourceNotFoundException("No such customer Exist");
	}
	
	@PatchMapping(path = "customer/update/{cusId}", consumes = "application/json", produces = "application/json")

	@Operation(summary = "Update Course")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Course updated", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Course.class)) }),
			@ApiResponse(responseCode = "404", description = "No course Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
			@ApiResponse(responseCode = "500", description = "internal server error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }) })
	@PutMapping(path = "course/update/{courseId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> updateCourse(@Validated @RequestBody Course course, @PathVariable int courseId,
			Errors error) {
		if (error.hasErrors()) {
			return ResponseEntity.status(400).body("error occured");
		}
		if (courseCon.getCourseId(courseId).isPresent()) {
			return ResponseEntity.status(202).body(courseCon.updateCourse(course, courseId));
		}
		throw new ResourceNotFoundException("No such course Exist");
	}
}
