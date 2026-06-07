package com.study.Main.Service;

import org.springframework.data.domain.Page;

import com.study.Main.Dto.GroupRequestDTO;
import com.study.Main.Dto.GroupResponseDTO;

public interface GroupService {
	GroupResponseDTO addGroup(GroupRequestDTO request);

	GroupResponseDTO getGroupById(Long groupId);

	Page<GroupResponseDTO> getGroupsByCompanyId(Long companyId, int page, int size);

	Page<GroupResponseDTO> getGroupsByParentId(Long parentGroupId, int page, int size);

	Page<GroupResponseDTO> getGroupsByType(String groupType, int page, int size);

	Page<GroupResponseDTO> getRootGroups(int page, int size);
}
