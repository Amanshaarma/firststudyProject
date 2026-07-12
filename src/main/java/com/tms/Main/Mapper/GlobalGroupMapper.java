package com.tms.Main.Mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.tms.Main.Dto.GlobalGroupRequestDTO;
import com.tms.Main.Dto.GlobalGroupResponseDTO;
import com.tms.Main.Model.GlobalGroup;

@Mapper(componentModel = "spring")
public interface GlobalGroupMapper {

	GlobalGroupResponseDTO toDTO(GlobalGroup globalGroup);

	@Mapping(target = "globalGroupId", ignore = true)
	GlobalGroup toEntity(GlobalGroupRequestDTO request);

	List<GlobalGroupResponseDTO> toDTOList(List<GlobalGroup> globalGroups);

	default Page<GlobalGroupResponseDTO> toDTOPage(Page<GlobalGroup> globalGroups) {
		return globalGroups.map(this::toDTO);
	}
}
