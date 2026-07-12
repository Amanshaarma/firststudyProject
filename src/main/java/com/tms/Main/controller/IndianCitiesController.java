package com.tms.Main.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.Main.Dto.CityDTO;
import com.tms.Main.Model.IndianCities;
import com.tms.Main.Service.IIndianCities;
import com.tms.Main.response.ApiResponsePattern;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class IndianCitiesController {
	private IIndianCities citiesService;

	public IndianCitiesController(IIndianCities citiesService) {
		this.citiesService = citiesService;
	}

	@GetMapping("/working")
	public String testApi()
	{
		return "this api is working";
	}

	@GetMapping("/allCities")
	public ResponseEntity<ApiResponsePattern<List<IndianCities>>> getAllCities() {
        List<IndianCities> cities = citiesService.getAllCities();
        return ResponseEntity.ok(ApiResponsePattern.success(cities));
    }
	@GetMapping("/{city}")
    public ResponseEntity<ApiResponsePattern<IndianCities>> getCityById(@PathVariable String city) {
		IndianCities cityGet = citiesService.getCityById(city);
        return ResponseEntity.ok(ApiResponsePattern.success(cityGet));
    }
	@PostMapping
    public ResponseEntity<ApiResponsePattern<CityDTO>> addCity(
            @Valid @RequestBody CityDTO request) {

        CityDTO savedCity = citiesService.addCity(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)          // 201 status
                .body(ApiResponsePattern.success(savedCity, "City added successfully"));
    }

}
