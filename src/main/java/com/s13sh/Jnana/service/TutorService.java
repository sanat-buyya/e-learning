package com.s13sh.Jnana.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.s13sh.Jnana.dto.CourseDto;
import com.s13sh.Jnana.dto.SectionDto;
import com.s13sh.Jnana.model.Course;
import com.s13sh.Jnana.model.Section;
import com.s13sh.Jnana.model.Tutor;
import com.s13sh.Jnana.repository.CourseRepository;
import com.s13sh.Jnana.repository.SectionRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Service
public class TutorService {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	SectionRepository sectionRepository;

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

	public String publishCourse(Long id, HttpSession session) {
		if (session.getAttribute("tutor") != null) {
			Course course = courseRepository.findById(id).orElseThrow();

			List<Section> sections = sectionRepository.findByCourse(course);

			if (course.getQuizQuestions().isEmpty() || sections.isEmpty()) {
				session.setAttribute("fail", "There Should be atleast one section and Quiz To Publish");
				return "redirect:/tutor/view-courses";
			} else {
				course.setPublished(true);
				session.setAttribute("success", "Course Published Success");
				return "redirect:/tutor/courses";
			}
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	public String loadAddSection(HttpSession session, Model model, SectionDto sectionDto) {
		if (session.getAttribute("tutor") != null) {

			List<Course> courses = courseRepository.findByTutor((Tutor) session.getAttribute("tutor"));
			if (courses.isEmpty()) {
				session.setAttribute("fail", "First Add Course to add Section");
				return "redirect:/tutor/courses";
			} else {
				model.addAttribute("courses", courses);
				model.addAttribute("sectionDto", sectionDto);
				return "add-section.html";
			}
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	public String addSection(@Valid SectionDto sectionDto, BindingResult result, HttpSession session) {
		if (session.getAttribute("tutor") != null) {
			if (result.hasErrors())
				return "add-section.html";
			else {
				Course course = courseRepository.findById(sectionDto.getCourseId()).orElseThrow();
				Section section = new Section();
				section.setCourse(course);
				section.setTitle(sectionDto.getTitle());
				section.setNotesUrl(saveNotes(sectionDto.getNotes()));
				section.setVideoUrl(saveVideo(sectionDto.getVideo()));
				sectionRepository.save(section);
				session.setAttribute("pass", "Section Added Success");
				return "redirect:/tutor/sections";
			}
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	String saveVideo(MultipartFile multipartFile) {
		return "";
	}

	String saveNotes(MultipartFile multipartFile) {
		return "";
	}

}