package com.study.Main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiClass 
{
	
	@GetMapping("/working")
	public String testApi()
	{
		return "this api is working";
	}
	

}
