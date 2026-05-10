package com.study.Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.study.Main.controller")
public class FirststudyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirststudyProjectApplication.class, args);
	}

}
