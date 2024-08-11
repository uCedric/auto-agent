package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		System.out.println("server is running on port");
		SpringApplication.run(ApiApplication.class, args);
	}
	// TODO: add external folder for the function that is responseble for connecting
	// to external sevice
}
