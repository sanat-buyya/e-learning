package com.s13sh.Jnana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.s13sh.Jnana.dto.UserDto;
import com.s13sh.Jnana.repository.LearnerRepository;
import com.s13sh.Jnana.repository.TutorRepository;

import jakarta.validation.Valid;

@Controller
public class GeneralController {

	@Autowired
	LearnerRepository learnerRepository;

	@Autowired
	TutorRepository tutorRepository;
	
	@GetMapping("/")
	public String loadHome() {
		return "home.html";
	}

	@GetMapping("/register")
	public String loadRegister(UserDto userDto, Model model) {
		model.addAttribute("userDto", userDto);
		return "register.html";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute @Valid UserDto userDto, BindingResult result) {
		if (!userDto.getConfirmPassword().equals(userDto.getPassword()))
			result.rejectValue("confirmPassword", "error.confirmPassword",
					"* Password and COnfirm Password not matching");

		if (learnerRepository.existsByMobile(userDto.getMobile())
				|| tutorRepository.existsByMobile(userDto.getMobile()))
			result.rejectValue("mobile", "error.mobile", "* Mobile Number Already in Use");

		if (learnerRepository.existsByEmail(userDto.getEmail()) || tutorRepository.existsByEmail(userDto.getEmail()))
			result.rejectValue("email", "error.email", "* Email Adress Already in Use");

		if (!result.hasErrors()) {
			return "otp.html";
		}
		return "register.html";
	}
}