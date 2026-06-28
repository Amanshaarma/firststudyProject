package com.study.Main.Service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.study.Main.Dto.CityDTO;
import com.study.Main.Expection.DuplicateResourceException;
import com.study.Main.Mapper.CityMapper;
import com.study.Main.Model.IndianCities;
import com.study.Main.Repository.indianCitiesRepository;
import com.study.Main.Service.IIndianCities;

import jakarta.transaction.Transactional;

@Service
public class IndianCitiesImpl implements IIndianCities{
	
	private indianCitiesRepository indianCitiesRepo;
	 private final CityMapper cityMapper;
	public IndianCitiesImpl(indianCitiesRepository indianCitiesRepo,CityMapper cityMapper )
	{ 
		this.indianCitiesRepo = indianCitiesRepo;
		 this.cityMapper = cityMapper;
	}

	@Override
	@Cacheable("cities")
	public List<IndianCities> getAllCities() {
		// TODO Auto-generated method stub
		List<IndianCities> ans = (List<IndianCities>) indianCitiesRepo.findAll();		
		return ans;
	}


	@Override
	public IndianCities getCityById(String city) {
		// TODO Auto-generated method stub
		IndianCities ans = indianCitiesRepo.findByCityName(city).get();
		return ans;
	}

	@Override
    @Transactional          // ensures all DB ops succeed or rollback
    @CacheEvict(value = "cities", allEntries = true)
    public CityDTO addCity(CityDTO request) {

        // Check if city already exists
        if (indianCitiesRepo.findByCityName(request.getCityName()).isPresent()) {
            throw new DuplicateResourceException("City already exists: " + request.getCityName());
        }

        // RequestDTO → Entity (MapStruct)
        IndianCities city = cityMapper.toEntity(request);

        // Save to DB
        IndianCities savedCity = indianCitiesRepo.save(city);
        
        // Entity → ResponseDTO (MapStruct)
        return cityMapper.toDTO(savedCity);
    }

}
