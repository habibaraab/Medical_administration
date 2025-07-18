package com.spring.Medicin_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedicinProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicinProjectApplication.class, args);
	}

}
