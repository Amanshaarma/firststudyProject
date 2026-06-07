package com.study.Main.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {

	private String cityName;
	private String districtName;
	private String stateName;

	// Constructors
	public CityDTO() {
	}

	public CityDTO(String cityName, String districtName, String stateName) {
		this.cityName = cityName;
		this.districtName = districtName;
		this.stateName = stateName;
	}

	// Getters & Setters

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}