package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		// TODO: initialize the db、redis connection

		SpringApplication.run(ApiApplication.class, args);
	}
	// TODO: add external folder for the function that is responseble for connecting
	// to external sevice
}
