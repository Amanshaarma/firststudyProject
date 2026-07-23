package com.tms.Main.Service;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import com.tms.Main.Dto.GroupRequestDTO;
import com.tms.Main.Dto.GroupResponseDTO;

public interface GroupService {
	GroupResponseDTO addGroup(GroupRequestDTO request);

    Map<String, Object> getGroupById(Long groupId,List<String> select);

	Page<Map<String, Object>> getGroups(Long companyId, List<String> select, int page, int size);

	Page<GroupResponseDTO> getGroupsByParentId(Long parentGroupId, int page, int size);

	Page<GroupResponseDTO> getGroupsByType(String groupType, int page, int size);

	Page<GroupResponseDTO> getRootGroups(int page, int size);

	void deleteGroup(Long groupId);

    GroupResponseDTO updateGroup(GroupRequestDTO request , Long groupId);
}
