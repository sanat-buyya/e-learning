package com.s13sh.Jnana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.Jnana.model.Course;
import com.s13sh.Jnana.model.EnrolledCourse;
import com.s13sh.Jnana.model.EnrolledSection;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {

	EnrolledCourse findByEnrolledSections(EnrolledSection section);

	List<EnrolledCourse> findByCourseIn(List<Course> courses);

}