package com.s13sh.Jnana.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Learner {
	@Id
	@GeneratedValue(generator = "learner_id")
	@SequenceGenerator(name = "learner_id", initialValue = 1, allocationSize = 2)
	private Long id;
	private String name;
	private String email;
	private String password;
	private Long mobile;
}