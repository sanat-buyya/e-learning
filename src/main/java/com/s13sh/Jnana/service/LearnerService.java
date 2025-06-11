package com.s13sh.Jnana.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.s13sh.Jnana.model.Course;
import com.s13sh.Jnana.model.Learner;
import com.s13sh.Jnana.repository.CourseRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class LearnerService {

	@Autowired
	CourseRepository courseRepository;

	@Value("${razor-pay.api.key}")
	String key;
	@Value("${razor-pay.api.secret}")
	String secret;

	public String loadHome(HttpSession session) {
		if (session.getAttribute("learner") != null) {
			return "leaner-home.html";
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	public String viewCourses(HttpSession session, Model model) {
		if (session.getAttribute("learner") != null) {
			List<Course> courses = courseRepository.findByPublishedTrue();
			if (courses.isEmpty()) {
				session.setAttribute("fail", "No Courses Available");
				return "redirect:/learner/home";
			} else {
				model.addAttribute("courses", courses);
				return "avaiable-courses.html";
			}
		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

	public String enrollCourse(HttpSession session, Long id, Model model) {
		if (session.getAttribute("learner") != null) {
			Learner learner = (Learner) session.getAttribute("learner");
			Course course = courseRepository.findById(id).get();

			if (course.isPaid()) {
				double amount = 199;
				try {
					RazorpayClient client = new RazorpayClient(key, secret);

					JSONObject object = new JSONObject();
					object.put("amount", amount * 100);
					object.put("currency", "INR");

					Order order = client.orders.create(object);
					String orderId = order.get("id");

					model.addAttribute("orderId", orderId);
					model.addAttribute("amount", amount * 100);
					model.addAttribute("currency", "INR");
					model.addAttribute("leaner", learner);
					model.addAttribute("key", key);

					return "payment.html";

				} catch (RazorpayException e) {
					e.printStackTrace();
					session.setAttribute("fail", "Something Went Wrong");
					return "redirect:/learner/home";
				}

			} else {
				session.setAttribute("pass", "Courses Enrolled Success, Thanks " + learner.getName());
				return "redirect:/learner/home";
			}

		} else {
			session.setAttribute("fail", "Invalid Session, Login First");
			return "redirect:/login";
		}
	}

}