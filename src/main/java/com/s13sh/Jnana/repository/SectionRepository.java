package com.s13sh.Jnana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.Jnana.model.Course;
import com.s13sh.Jnana.model.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {

	List<Section> findByCourse(Course course);

}