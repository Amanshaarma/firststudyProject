package com.tms.Main.Service;

import org.springframework.data.domain.Page;

import com.tms.Main.Dto.GlobalGroupRequestDTO;
import com.tms.Main.Dto.GlobalGroupResponseDTO;

//service/GlobalGroupService.java
public interface GlobalGroupService {
	GlobalGroupResponseDTO addGlobalGroup(GlobalGroupRequestDTO request);

	GlobalGroupResponseDTO getGlobalGroupById(Long globalGroupId);

	Page<GlobalGroupResponseDTO> getAllGlobalGroups(int page, int size);

	Page<GlobalGroupResponseDTO> searchByGroupName(String groupName, int page, int size);
}