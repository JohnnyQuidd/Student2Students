package com.student2students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class Student2StudentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Student2StudentsApplication.class, args);
	}

}
