package com.student2students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Student2StudentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Student2StudentsApplication.class, args);
	}

}
