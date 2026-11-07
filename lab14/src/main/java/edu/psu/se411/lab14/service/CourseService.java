package edu.psu.se411.lab14.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.psu.se411.lab14.model.Course;
import edu.psu.se411.lab14.repository.CourseRepository;

@Service
public class CourseService {

	private CourseRepository courseRepository;

	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public Iterable<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	public Optional<Course> getCourseById(Long id) {
		return courseRepository.findById(id);
	}

	public Course createCourse(Course course) {
		return courseRepository.save(course);
	}

	// returns false if the course does not exist
	public boolean updateCourse(Long id, Course course) {
		Optional<Course> existingCourse = courseRepository.findById(id);
		if (existingCourse.isPresent()) {
			Course updatedCourse = new Course(existingCourse.get().id(), course.code(), course.name(),
					course.description(), course.credits()); // new object with the same id
			courseRepository.save(updatedCourse);
			return true;
		}
		return false;
	}

	// returns false if the course does not exist
	public boolean deleteCourse(Long id) {
		Optional<Course> existingCourse = courseRepository.findById(id);
		if (existingCourse.isPresent()) {
			courseRepository.delete(existingCourse.get());
			return true;
		}
		return false;
	}
}
