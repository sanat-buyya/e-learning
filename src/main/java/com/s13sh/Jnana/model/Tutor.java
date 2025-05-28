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
public class Tutor {
	@Id
	@GeneratedValue(generator = "tutor_id")
	@SequenceGenerator(name = "tutor_id", initialValue = 2, allocationSize = 2)
	private Long id;
	private String name;
	private String email;
	private String password;
	private Long mobile;
}