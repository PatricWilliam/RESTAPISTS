package com.vi.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vi.model.Cart;
import com.vi.model.Course;
import com.vi.repo.CartRepostory;
import com.vi.repo.CouseRepostory;

@Controller
public class CartController {

	@Autowired
	public CartRepostory cartRepo;
	@Autowired
	public CouseRepostory courseRepo;

	public boolean cartAvailbale(int cartId) {

		return cartRepo.existsById(cartId);

	}

	public Cart addToCart(Cart cart, int cartId) {
		Optional<Cart> c = cartRepo.findById(cartId);
		Set<Course> newCourse = new HashSet<Course>();
		Cart existingCart = c.get();
		for (Course course : cart.getCourse()) {
			if (courseRepo.findById(course.getCourseId()).isPresent()) {
				newCourse.add(courseRepo.findById(course.getCourseId()).get());
			}
		}
		if (!newCourse.isEmpty()) {
			existingCart.setCourse(newCourse);
		}
		return cartRepo.save(existingCart);
	}

	public Cart removeFromCart(Course course, int cartId) {
		Optional<Cart> c = cartRepo.findById(cartId);
		Cart existingCart = c.get();
		Set<Course> courses = existingCart.getCourse();
		for (Course existingCourse : courses) {
			if (existingCourse.getCourseId() == course.getCourseId()) {
				courses.remove(existingCourse);
			}
		}
		existingCart.setCourse(courses);
		return cartRepo.save(existingCart);
	}

}
