package com.vi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vi.model.Course;
import com.vi.model.CustomerDetails;
import com.vi.repo.CouseRepostory;

@Controller
public class CourseController {
	@Autowired
	public CouseRepostory courserepo;

	public List<Course> createCourse(List<Course> courses) {
		return courserepo.saveAll(courses);
	}

	public boolean findAllCourseByCourseId(List<Course> course) {
		for (Course c : course) {
			if (courserepo.existsById(c.getCourseId())) {
				return true;
			}
		}
		return false;
	}

	public List<Course> getAllCourse() {
		return courserepo.findAll();
	}
	
	public Optional<Course> getCourseId(int courseId) {
		return courserepo.findById(courseId);
	}

	
	public Course updateCourse(Course course,int courseId) {
		Course existingCourse=courserepo.findById(courseId).get();
		
		if (course.getDiscount()!= 0) {
			existingCourse.setDiscount(course.getDiscount());
		}
		
		return courserepo.save(existingCourse);
	}
}
