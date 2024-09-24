package com.springShoppingBot.SpringShoppingBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringShoppingBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShoppingBotApplication.class, args);
	}

	@GetMapping
	public String greetings(){
		return "We wish you successful shopping";
	}

}
