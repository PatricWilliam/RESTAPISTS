package com.vi.model;

import java.util.HashSet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.v3.oas.annotations.media.Schema;

@Component
@Entity
@Table(name = "Course")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "courseId")
@JsonIgnoreProperties(value = { "carts", "cart" })
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id")
	@SequenceGenerator(sequenceName = "course_id", allocationSize = 100, name = "course_id")
	public int courseId;

	@Column(nullable = false)
	@NotBlank
	public String courseName;

	@Column(nullable = false)
	public String description;

	@Column
	@NotBlank
	public double courseFee;

	@Column(nullable = false)
	@NotBlank
	public double duration;

	@Column
	public double discount;

	@Column
	public boolean isDiscountApplicable;

	@Column
	public boolean isStockAvailable;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "courses", fetch = FetchType.LAZY)
	
	@Schema(hidden = true)
	public Set<Cart> carts = new HashSet<Cart>();

	public Set<Cart> getCart() {
		return carts;
	}

	public void setCart(Set<Cart> cart) {
		this.carts = cart;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCourseFee() {
		return courseFee;
	}

	public void setCourseFee(double courseFee) {
		this.courseFee = courseFee;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public boolean isDiscountApplicable() {
		return isDiscountApplicable;
	}

	public void setDiscountApplicable(boolean isDiscountApplicable) {
		this.isDiscountApplicable = isDiscountApplicable;
	}

	public boolean isStockAvailable() {
		return isStockAvailable;
	}

	public void setStockAvailable(boolean isStockAvailable) {
		this.isStockAvailable = isStockAvailable;
	}

}
