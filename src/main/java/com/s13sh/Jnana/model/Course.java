package com.s13sh.Jnana.model;

import java.util.ArrayList;
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
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private boolean paid;
	private boolean published;

	@ManyToOne
	Tutor tutor;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<QuizQuestion> quizQuestions = new ArrayList<QuizQuestion>();

}