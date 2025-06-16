package com.s13sh.Jnana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.Jnana.model.EnrolledCourse;
import com.s13sh.Jnana.model.Learner;

public interface LearnerRepository extends JpaRepository<Learner, Long> {

	boolean existsByMobile(long mobile);

	boolean existsByEmail(String email);

	Learner findByEmail(String email);

	List<Learner> findByEnrolledCoursesIn(List<EnrolledCourse> enrolledCourses);

}