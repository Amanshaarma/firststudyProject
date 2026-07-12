package com.tms.Main.Service;

import java.util.List;

import com.tms.Main.Dto.CityDTO;
import com.tms.Main.Model.IndianCities;

public interface IIndianCities 
{
	List<IndianCities> getAllCities();

	IndianCities getCityById(String city); 

	CityDTO addCity(CityDTO request);
}
