package com.s13sh.Jnana.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class EnrolledCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Course course;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<EnrolledSection> enrolledSections;
	
	private boolean courseCompleted;
	private boolean finalQuizCompleted;
	private LocalDate completionDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public List<EnrolledSection> getEnrolledSections() {
		return enrolledSections;
	}
	public void setEnrolledSections(List<EnrolledSection> enrolledSections) {
		this.enrolledSections = enrolledSections;
	}
	public boolean isCourseCompleted() {
		return courseCompleted;
	}
	public void setCourseCompleted(boolean courseCompleted) {
		this.courseCompleted = courseCompleted;
	}
	public boolean isFinalQuizCompleted() {
		return finalQuizCompleted;
	}
	public void setFinalQuizCompleted(boolean finalQuizCompleted) {
		this.finalQuizCompleted = finalQuizCompleted;
	}
	public LocalDate getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(LocalDate completionDate) {
		this.completionDate = completionDate;
	}
	
	
}