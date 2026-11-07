package edu.psu.se411.lab14.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.psu.se411.lab14.model.Course;
import edu.psu.se411.lab14.service.CourseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/courses")
public class CourceController {

	private final CourseService courseService;

	public CourceController(CourseService courseService) {
		this.courseService = courseService;
	}

	// GET /api/v1/courses -> 200
	@GetMapping()
	public ResponseEntity<Iterable<Course>> getAllCourses() {
		return ResponseEntity.ok(courseService.getAllCourses());
	}

	// GET /api/v1/courses/{id} -> 200, 404
	@GetMapping("/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
		Optional<Course> op = courseService.getCourseById(id);
		if (op.isPresent()) {
			return ResponseEntity.ok(op.get());
		}
		return ResponseEntity.notFound().build();
	}

	// POST /api/v1/courses -> 201 + location, 400 invalid body / id sent, 422 save fails
	@PostMapping
	public ResponseEntity<Void> createCourse(@Valid @RequestBody Course course, UriComponentsBuilder ucb) {
		if (course.id() != null) {
			return ResponseEntity.badRequest().build();
		}
		try {
			Course createdCourse = courseService.createCourse(course);
			URI uri = ucb.path("/api/v1/courses/{id}").buildAndExpand(createdCourse.id()).toUri();
			return ResponseEntity.created(uri).build();
		} catch (Exception ex) {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	// PUT /api/v1/courses/{id} -> 204, 404
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCourse(@PathVariable Long id, @Valid @RequestBody Course course) {
		if (courseService.updateCourse(id, course)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	// DELETE /api/v1/courses/{id} -> 204, 404
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
		if (courseService.deleteCourse(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
