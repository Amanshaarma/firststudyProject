package com.study.Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ComponentScan("com.study.Main.controller")
@ComponentScan("com.study.Main.Service")  
@ComponentScan("com.study.Main.Mapper")
@ComponentScan("com.study.Main.Expection")
@ComponentScan("com.study.Main.*")
public class FirststudyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirststudyProjectApplication.class, args); 
//		System.out.println(new BCryptPasswordEncoder().encode("CryPmin@436AMShar"));
	}

}
