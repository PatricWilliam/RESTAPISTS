package com.vi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Component
@Entity
@Table(name = "Cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id")
	@SequenceGenerator(sequenceName = "cart_id", allocationSize = 30, name = "cart_id")
	public int cartId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customerId", nullable = false)
	@JsonBackReference(value = "customerDetails")
	@Schema(hidden = true)
	public CustomerDetails customerDetails;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "CartCourseRelation", joinColumns = @JoinColumn(name = "cartId", referencedColumnName = "cartId", table = "Cart"), inverseJoinColumns = @JoinColumn(name = "courseId", referencedColumnName = "courseId", table = "Course"))
	@JsonBackReference(value = "courses")
	@Schema(hidden = true)
	public Set<Course> courses = new HashSet<Course>();

	public Set<Course> getCourse() {
		return courses;

	}

	public void setCourse(Set<Course> course) {
		this.courses = course;

	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

}
