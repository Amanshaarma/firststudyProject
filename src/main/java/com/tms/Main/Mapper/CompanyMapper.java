package com.study.Main.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.study.Main.Dto.CompanyRequestDTO;
import com.study.Main.Dto.CompanyResponseDTO;
import com.study.Main.Model.CompanyProfiles;

//mapper/CompanyMapper.java
//S - only responsible for mapping
//D - depends on interface not implementation
@Mapper(componentModel = "spring")
public interface CompanyMapper {

 // Entity → ResponseDTO
 @Mapping(source = "user.userId", target = "userId")
 CompanyResponseDTO toDTO(CompanyProfiles company);

 // RequestDTO → Entity
 @Mapping(target = "companyId", ignore = true)
 @Mapping(target = "user", ignore = true)   // set manually in service
 @Mapping(target = "createdAt", ignore = true)
 @Mapping(target = "updatedAt", ignore = true)
 CompanyProfiles toEntity(CompanyRequestDTO request);

 List<CompanyResponseDTO> toDTOList(List<CompanyProfiles> companies);
}