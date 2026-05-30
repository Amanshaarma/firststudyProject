package com.study.Main.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.study.Main.Model.IndianCities;
import com.study.Main.Repository.indianCitiesRepository;
import com.study.Main.Service.IIndianCities;

@Service
public class IndianCitiesImpl implements IIndianCities{
	
	private indianCitiesRepository indianCitiesRepo;
	public IndianCitiesImpl(indianCitiesRepository indianCitiesRepo)
	{ 
		this.indianCitiesRepo = indianCitiesRepo;
	}

	@Override
	public List<IndianCities> getAllCities() {
		// TODO Auto-generated method stub
		long now = System.currentTimeMillis();
		List<IndianCities> ans = indianCitiesRepo.findAll();
		long after = System.currentTimeMillis();		
		return ans;
	}

}
