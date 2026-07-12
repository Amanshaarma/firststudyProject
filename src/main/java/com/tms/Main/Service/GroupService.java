package com.study.Main.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.study.Main.Dto.GroupRequestDTO;
import com.study.Main.Dto.GroupResponseDTO;

public interface GroupService {
	GroupResponseDTO addGroup(GroupRequestDTO request);

    Map<String, Object> getGroupById(Long groupId,List<String> select);

	Page<Map<String, Object>> getGroups(Long companyId, List<String> select, int page, int size);

	Page<GroupResponseDTO> getGroupsByParentId(Long parentGroupId, int page, int size);

	Page<GroupResponseDTO> getGroupsByType(String groupType, int page, int size);

	Page<GroupResponseDTO> getRootGroups(int page, int size);

	void deleteGroup(Long groupId);
}
