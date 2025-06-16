package com.s13sh.Jnana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.s13sh.Jnana.dto.CourseDto;
import com.s13sh.Jnana.dto.SectionDto;
import com.s13sh.Jnana.service.TutorService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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

	@GetMapping("/learners")
	public String loadLearners(HttpSession session, Model model) {
		return tutorService.loadLearners(session, model);
	}

	@GetMapping("/add-course")
	public String loadAddCourse(HttpSession session, Model model, CourseDto courseDto) {
		return tutorService.addCourse(session, model, courseDto);
	}

	@PostMapping("/add-course")
	public String addCourse(@ModelAttribute @Valid CourseDto courseDto, BindingResult result, HttpSession session) {
		return tutorService.addCourse(session, courseDto, result);
	}

	@GetMapping("/view-courses")
	public String viewCourses(HttpSession session, Model model) {
		return tutorService.viewCourses(session, model);
	}

	@GetMapping("/publish/{id}")
	public String publishCourse(@PathVariable Long id, HttpSession session) {
		return tutorService.publishCourse(id, session);
	}

	@GetMapping("/add-section")
	public String loadAddSection(HttpSession session, Model model, SectionDto sectionDto) {
		return tutorService.loadAddSection(session, model, sectionDto);
	}

	@PostMapping("/add-section")
	public String addSection(@ModelAttribute @Valid SectionDto sectionDto, BindingResult result, Model model,
			HttpSession session) {
		return tutorService.addSection(sectionDto, result, model, session);
	}

	@GetMapping("/view-sections")
	public String viewSections(HttpSession session, Model model) {
		return tutorService.viewSections(session, model);
	}

}