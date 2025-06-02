package com.s13sh.Jnana.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.s13sh.Jnana.dto.AccountType;
import com.s13sh.Jnana.dto.UserDto;
import com.s13sh.Jnana.model.Learner;
import com.s13sh.Jnana.model.Tutor;
import com.s13sh.Jnana.repository.LearnerRepository;
import com.s13sh.Jnana.repository.TutorRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class GeneralService {
	@Autowired
	LearnerRepository learnerRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	TutorRepository tutorRepository;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	TemplateEngine templateEngine;

	@Value("${OTP_TIME}")
	long otpTime;

	@Value("${spring.mail.username}")
	private String email;

	public String loadRegister(UserDto userDto, Model model) {
		model.addAttribute("userDto", userDto);
		return "register.html";
	}

	public String register(UserDto userDto, BindingResult result, HttpSession session) {
		if (!userDto.getConfirmPassword().equals(userDto.getPassword()))
			result.rejectValue("confirmPassword", "error.confirmPassword",
					"* Password and COnfirm Password not matching");

		if (learnerRepository.existsByMobile(userDto.getMobile())
				|| tutorRepository.existsByMobile(userDto.getMobile()))
			result.rejectValue("mobile", "error.mobile", "* Mobile Number Already in Use");

		if (learnerRepository.existsByEmail(userDto.getEmail()) || tutorRepository.existsByEmail(userDto.getEmail()))
			result.rejectValue("email", "error.email", "* Email Adress Already in Use");

		if (!result.hasErrors()) {
			int otp = new Random().nextInt(100000, 1000000);
			session.setAttribute("otp", otp);
			session.setAttribute("userDto", userDto);
			sendEmail(otp, userDto);
			session.setAttribute("time", LocalDateTime.now());
			session.setAttribute("pass", "Otp Sent Success");
			return "redirect:/otp";
		}
		return "register.html";
	}

	public String confirmOtp(int otp, HttpSession session) {
		LocalDateTime createdTime = (LocalDateTime) session.getAttribute("time");
		LocalDateTime curretTime = LocalDateTime.now();

		long seconds = Duration.between(createdTime, curretTime).getSeconds();
		if (seconds <= otpTime) {
			int sessionOtp = (int) session.getAttribute("otp");
			UserDto userDto = (UserDto) session.getAttribute("userDto");

			if (sessionOtp == otp) {
				if (userDto.getType() == AccountType.TUTOR) {
					Tutor tutor = new Tutor();
					tutor.setEmail(userDto.getEmail());
					tutor.setMobile(userDto.getMobile());
					tutor.setName(userDto.getName());
					tutor.setPassword(encoder.encode(userDto.getPassword()));

					tutorRepository.save(tutor);
				} else {
					Learner learner = new Learner();
					learner.setEmail(userDto.getEmail());
					learner.setMobile(userDto.getMobile());
					learner.setName(userDto.getName());
					learner.setPassword(encoder.encode(userDto.getPassword()));

					learnerRepository.save(learner);
				}
				session.setAttribute("pass", "Account Created Success");
				return "redirect:/";
			} else {
				session.setAttribute("fail", "Invalid Otp Try Again");
				return "redirect:/otp";
			}
		} else {
			session.setAttribute("fail", "Otp Expired at Try Resending OTP ");
			return "redirect:/otp";
		}
	}

	void sendEmail(int otp, UserDto userDto) {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(userDto.getEmail());
			helper.setFrom(email, "Elearning Platform");
			helper.setSubject("Verify OTP");

			Context context = new Context();
			context.setVariable("otp", otp);
			context.setVariable("name", userDto.getName());
			context.setVariable("role", userDto.getType().name());

			String body = templateEngine.process("email-template.html", context);

			helper.setText(body, true);

			mailSender.send(message);
		} catch (Exception e) {
			System.err.println("Failed to Send OTP : " + otp);
		}

	}

	public void removeMessage() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
		HttpServletRequest request = attributes.getRequest();
		HttpSession session = request.getSession(true);

		session.removeAttribute("pass");
		session.removeAttribute("fail");
	}

	public String resendOtp(HttpSession session) {
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		int otp = new Random().nextInt(100000, 1000000);
		session.setMaxInactiveInterval(60);
		session.setAttribute("otp", otp);
		session.setAttribute("userDto", userDto);
		sendEmail(otp, userDto);

		session.setAttribute("pass", "Otp Re-Sent Success");
		return "redirect:/otp";
	}

	public String login(String email, String password, HttpSession session) {
		Learner learner = learnerRepository.findByEmail(email);
		Tutor tutor = tutorRepository.findByEmail(email);

		if (learner == null && tutor == null) {
			session.setAttribute("fail", "Invalid Email");
			return "redirect:/login";
		} else {
			if (tutor != null) {
				if (encoder.matches(password, tutor.getPassword())) {
					session.setAttribute("pass", "Login Success as Tutor");
					session.setAttribute("tutor", tutor);
					return "redirect:/tutor/home";
				} else {
					session.setAttribute("fail", "Invalid Password");
					return "redirect:/login";
				}
			} else {
				if (encoder.matches(password, learner.getPassword())) {
					session.setAttribute("pass", "Login Success as Learner");
					session.setAttribute("learner", learner);
					return "redirect:/learner/home";
				} else {
					session.setAttribute("fail", "Invalid Password");
					return "redirect:/login";
				}
			}
		}
	}

	public String logout(HttpSession session) {
		session.removeAttribute("learner");
		session.removeAttribute("tutor");
		session.setAttribute("fail", "Logout Success");
		return "redirect:/";
	}

}