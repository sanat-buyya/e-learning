package com.s13sh.Jnana.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseDto {
	@Size(min = 5, max = 100, message = "* Title Should be 5~100 charecters")
	private String title;
	@Size(min = 10, max = 500, message = "* Title Should be 10~500 charecters")
	private String description;
	@NotNull(message = "* Select Any One")
	private boolean paid;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	
	
	
	
}