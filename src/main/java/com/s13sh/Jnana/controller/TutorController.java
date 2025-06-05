package com.s13sh.Jnana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.s13sh.Jnana.service.TutorService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tutor")
public class TutorController {

	@Autowired
	TutorService tutorService;

	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		return tutorService.loadHome(session);
	}

	@GetMapping("/courses")
	public String loadCourses(HttpSession session) {
		return tutorService.loadCourses(session);
	}

	@GetMapping("/sections")
	public String loadSections(HttpSession session) {
		return tutorService.loadSections(session);
	}

	@GetMapping("/questions")
	public String loadQuestions(HttpSession session) {
		return tutorService.loadQuestions(session);
	}

	@GetMapping("/learners")
	public String loadLearners(HttpSession session) {
		return tutorService.loadLearners(session);
	}

}