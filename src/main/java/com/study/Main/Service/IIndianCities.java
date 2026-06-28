package com.study.Main.Service;

import java.util.List;

import com.study.Main.Dto.CityDTO;
import com.study.Main.Model.IndianCities;

public interface IIndianCities 
{
	List<IndianCities> getAllCities();

	IndianCities getCityById(String city); 

	CityDTO addCity(CityDTO request);
}
