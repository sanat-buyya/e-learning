package com.s13sh.Jnana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.Jnana.model.Course;
import com.s13sh.Jnana.model.Tutor;

public interface CourseRepository extends JpaRepository<Course, Long> {

	List<Course> findByTutor(Tutor attribute);

	List<Course> findByPublishedTrue();

}