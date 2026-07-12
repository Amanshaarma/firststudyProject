package com.tms.Main.Mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.tms.Main.Dto.CityDTO;
import com.tms.Main.Model.IndianCities;

//CityMapper.java
@Mapper(componentModel = "spring")
public interface CityMapper {

 // Single object mapping
 CityDTO toDTO(IndianCities city);

 // List mapping — MapStruct generates this automatically!
 List<CityDTO> toDTOList(List<IndianCities> cities);

 IndianCities toEntity(CityDTO request);
}