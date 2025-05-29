package com.s13sh.Jnana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.s13sh.Jnana.dto.UserDto;
import com.s13sh.Jnana.service.GeneralService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class GeneralController {

	@Autowired
	GeneralService generalService;

	@GetMapping("/")
	public String loadHome() {
		return "home.html";
	}

	@GetMapping("/register")
	public String loadRegister(UserDto userDto, Model model) {
		return generalService.loadRegister(userDto, model);
	}

	@PostMapping("/register")
	public String register(@ModelAttribute @Valid UserDto userDto, BindingResult result, HttpSession session) {
		return generalService.register(userDto, result, session);
	}

	@GetMapping("/otp")
	public String loadOtp() {
		return "otp.html";
	}

	@PostMapping("/submit-otp")
	public String submitOtp(@RequestParam int otp, HttpSession session) {
		return generalService.confirmOtp(otp, session);
	}
}