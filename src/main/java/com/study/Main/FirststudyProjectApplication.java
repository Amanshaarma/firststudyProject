package com.study.Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.study.Main.controller")
@ComponentScan("com.study.Main.Service")  
@ComponentScan("com.study.Main.Mapper")
@ComponentScan("com.study.Main.Expection")
@ComponentScan("com.study.Main.*")
@ComponentScan("com.study.Main.util")
@EnableCaching
public class FirststudyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirststudyProjectApplication.class, args); 
//		System.out.println(new BCryptPasswordEncoder().encode("CryPmin@436AMShar"));
	}

}
