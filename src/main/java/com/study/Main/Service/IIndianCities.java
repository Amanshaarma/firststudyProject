package com.study.Main.Service;

import java.util.List;

import com.study.Main.Dto.CityDTO;

public interface IIndianCities 
{
	List<CityDTO> getAllCities();

	CityDTO getCityById(String city); 

	CityDTO addCity(CityDTO request);
}
