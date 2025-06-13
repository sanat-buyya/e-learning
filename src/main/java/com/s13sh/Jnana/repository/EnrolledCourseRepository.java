package com.s13sh.Jnana.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.Jnana.model.EnrolledCourse;
import com.s13sh.Jnana.model.EnrolledSection;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {

	EnrolledCourse findByEnrolledSections(EnrolledSection section);

}