package com.study.Main.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.study.Main.Dto.GroupRequestDTO;
import com.study.Main.Dto.GroupResponseDTO;
import com.study.Main.Model.Group;


@Mapper(componentModel = "spring")
public interface GroupMapper {

 @Mapping(source = "companyProfile.companyId", target = "companyId")
 @Mapping(source = "parentGroup.groupId", target = "parentGroupId")
 @Mapping(source = "globalGroup.globalGroupId", target = "globalGroupId")  // ✅ add
 GroupResponseDTO toDTO(Group group);

 @Mapping(target = "groupId", ignore = true)
 @Mapping(target = "companyProfile", ignore = true)
 @Mapping(target = "parentGroup", ignore = true)
 @Mapping(target = "globalGroup", ignore = true)   // ✅ add — set manually in service
 @Mapping(target = "createdAt", ignore = true)
 @Mapping(target = "updatedAt", ignore = true)
 Group toEntity(GroupRequestDTO request);

 List<GroupResponseDTO> toDTOList(List<Group> groups);

 default Page<GroupResponseDTO> toDTOPage(Page<Group> groups) {
     return groups.map(this::toDTO);
 }
}