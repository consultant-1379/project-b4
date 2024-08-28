package com.ericsson.retrotool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RetrotoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetrotoolApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello Team 2";
	}
}
