package com.study.Main.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.Main.Model.IndianCities;
import com.study.Main.Service.IIndianCities;

@RestController
public class ApiClass {
	private IIndianCities citiesService;

	public ApiClass(IIndianCities citiesService) {
		this.citiesService = citiesService;
	}

	@GetMapping("/working")
	public String testApi()
	{
		return "this api is working";
	}

	@GetMapping("/allCities")
	public List<IndianCities> getIndianCities()
	{
			return citiesService.getAllCities();	
	}

}
