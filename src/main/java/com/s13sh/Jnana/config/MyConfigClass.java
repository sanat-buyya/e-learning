package com.s13sh.Jnana.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cloudinary.Cloudinary;

@Configuration
public class MyConfigClass {

	@Value("${cloudinary.api.key}")
	private String cloudinary_key;
	@Value("${cloudinary.api.secret}")
	private String cloudinary_secret;
	@Value("${cloudinary.api.cloud_name}")
	private String cloudinary_name;

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	Cloudinary cloudinary() {
		String url = "cloudinary://" + cloudinary_key + ":" + cloudinary_secret + "@" + cloudinary_name;
		return new Cloudinary(url);
	}
}