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
    private Long id;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "state_name")
    private String stateName;

	@Override
	public String toString() {
		return "IndianCities [id=" + id + ", cityName=" + cityName + ", districtName=" + districtName + ", stateName="
				+ stateName + "]";
	}
    
    
}