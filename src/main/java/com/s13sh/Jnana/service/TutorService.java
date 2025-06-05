package com.s13sh.Jnana.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.s13sh.Jnana.dto.CourseDto;
import com.s13sh.Jnana.model.Course;
import com.s13sh.Jnana.model.Tutor;
import com.s13sh.Jnana.repository.CourseRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service
public class TutorService {

	@Autowired
	CourseRepository courseRepository;

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

	public String addCourse(HttpSession session, Model model, CourseDto courseDto) {
		if (session.getAttribute("tutor") != null) {
			model.addAttribute("courseDto", courseDto);
			return "add-course.html";
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	public String addCourse(HttpSession session, @Valid CourseDto courseDto, BindingResult result) {
		if (session.getAttribute("tutor") != null) {
			if (result.hasErrors())
				return "add-course.html";
			else {
				Course course = new Course();
				course.setTitle(courseDto.getTitle());
				course.setPaid(courseDto.isPaid());
				course.setDescription(courseDto.getDescription());
				course.setTutor((Tutor) session.getAttribute("tutor"));
				courseRepository.save(course);
				session.setAttribute("pass", "Course Added Success");
				return "redirect:/tutor/courses";
			}
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	public String viewCourses(HttpSession session, Model model) {
		if (session.getAttribute("tutor") != null) {
			List<Course> courses = courseRepository.findByTutor((Tutor) session.getAttribute("tutor"));
			if (courses.isEmpty()) {
				session.setAttribute("fail", "No Courses Added Yet");
				return "redirect:/tutor/courses";
			} else {
				model.addAttribute("courses", courses);
				return "view-courses.html";
			}

		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

}