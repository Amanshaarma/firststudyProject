package com.tms.Main.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "indian_cities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IndianCities {

    @Id
    @Column(name = "city_id")
    private Integer id;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "state_name")
    private String stateName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "IndianCities [id=" + id + ", cityName=" + cityName + ", districtName=" + districtName + ", stateName="
				+ stateName + "]";
	}
    
    
}