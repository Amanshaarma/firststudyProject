package com.tms.Main.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.Main.Model.IndianCities;

@Repository
public interface indianCitiesRepository extends JpaRepository<IndianCities,Integer> 
{
	Optional<IndianCities> findByCityName(String city);
}
