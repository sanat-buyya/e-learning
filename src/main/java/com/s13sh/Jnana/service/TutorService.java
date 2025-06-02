package com.s13sh.Jnana.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class TutorService {

	public String loadHome(HttpSession session) {
		if (session.getAttribute("tutor") != null) {
			return "tutor-home.html";
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	public String loadCourses(HttpSession session) {
		if (session.getAttribute("tutor") != null) {
			return "manage-course.html";
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	public String loadSections(HttpSession session) {
		if (session.getAttribute("tutor") != null) {
			return "manage-section.html";
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	public String loadQuestions(HttpSession session) {
		if (session.getAttribute("tutor") != null) {
			return "manage-question.html";
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	public String loadLearners(HttpSession session) {
		if (session.getAttribute("tutor") != null) {
			return "tutor-home.html";
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

}