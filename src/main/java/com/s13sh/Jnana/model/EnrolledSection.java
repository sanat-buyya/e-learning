package com.s13sh.Jnana.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class EnrolledSection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Section section;
	private boolean sectionCompleted;
	private boolean sectionQuizCompleted;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	public boolean isSectionCompleted() {
		return sectionCompleted;
	}
	public void setSectionCompleted(boolean sectionCompleted) {
		this.sectionCompleted = sectionCompleted;
	}
	public boolean isSectionQuizCompleted() {
		return sectionQuizCompleted;
	}
	public void setSectionQuizCompleted(boolean sectionQuizCompleted) {
		this.sectionQuizCompleted = sectionQuizCompleted;
	}
	
	
}