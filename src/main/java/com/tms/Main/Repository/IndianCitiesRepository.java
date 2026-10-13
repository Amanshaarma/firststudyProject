package com.tms.Main.Repository;

import com.tms.Main.Model.IndianCities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndianCitiesRepository extends JpaRepository<IndianCities,Long>
{
	Optional<IndianCities> findByCityName(String city);

}
