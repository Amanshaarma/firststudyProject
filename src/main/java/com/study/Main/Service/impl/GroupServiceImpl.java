package com.study.Main.Service.impl;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.study.Main.Dto.GroupRequestDTO;
import com.study.Main.Dto.GroupResponseDTO;
import com.study.Main.Expection.DuplicateResourceException;
import com.study.Main.Expection.ResourceNotFoundException;
import com.study.Main.Mapper.GroupMapper;
import com.study.Main.Model.CompanyProfiles;
import com.study.Main.Model.GlobalGroup;
import com.study.Main.Model.Group;
import com.study.Main.Repository.CompanyRepository;
import com.study.Main.Repository.GlobalGroupRepository;
import com.study.Main.Repository.GroupRepository;
import com.study.Main.Service.GroupService;

import jakarta.transaction.Transactional;

@Service
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepository;
	private final CompanyRepository companyProfileRepository;
	private final GroupMapper groupMapper;
	private final GlobalGroupRepository globalGroupRepository; 

	// D - Constructor injection
	public GroupServiceImpl(GroupRepository groupRepository, CompanyRepository companyProfileRepository,
			GroupMapper groupMapper, GlobalGroupRepository globalGroupRepository) {
		this.groupRepository = groupRepository;
		this.companyProfileRepository = companyProfileRepository;
		this.groupMapper = groupMapper;
		this.globalGroupRepository = globalGroupRepository;
	}

	@Override
	@Transactional
	public GroupResponseDTO addGroup(GroupRequestDTO request) {

		// 1. Validate company exists
		CompanyProfiles companyProfile = companyProfileRepository.findById(request.getCompanyId()).orElseThrow(
				() -> new ResourceNotFoundException("Company not found with id: " + request.getCompanyId()));

		// 2. Duplicate group name check within same company
		if (groupRepository.existsByGroupNameAndCompanyProfileCompanyId(request.getGroupName(),
				request.getCompanyId())) {
			throw new DuplicateResourceException("Group already exists: " + request.getGroupName());
		}

		// 3. Map RequestDTO → Entity
		Group group = groupMapper.toEntity(request);
		group.setCompanyProfile(companyProfile);

		// 4. Set parent group if provided
		if (request.getParentGroupId() != null) {
			Group parentGroup = groupRepository.findById(request.getParentGroupId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Parent group not found with id: " + request.getParentGroupId()));
			group.setParentGroup(parentGroup);
		}
		if (request.getGlobalGroupId() != null) {
			GlobalGroup globalGroup = globalGroupRepository.findById(request.getGlobalGroupId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Global group not found with id: " + request.getGlobalGroupId()));
			group.setGlobalGroup(globalGroup);
		}

		// 5. Save & return
		return groupMapper.toDTO(groupRepository.save(group));
	}

	@Override
	public GroupResponseDTO getGroupById(Long groupId) {
		return groupMapper.toDTO(groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId)));
	}

	@Override
	public Page<GroupResponseDTO> getGroupsByCompanyId(Long companyId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		return groupMapper.toDTOPage(groupRepository.findByCompanyProfileCompanyId(companyId, pageable));
	}

	@Override
	public Page<GroupResponseDTO> getGroupsByParentId(Long parentGroupId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("groupName").ascending());
		return groupMapper.toDTOPage(groupRepository.findByParentGroupGroupId(parentGroupId, pageable));
	}

	@Override
	public Page<GroupResponseDTO> getGroupsByType(String groupType, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		return groupMapper.toDTOPage(groupRepository.findByGroupType(groupType, pageable));
	}

	@Override
	public Page<GroupResponseDTO> getRootGroups(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("groupName").ascending());
		return groupMapper.toDTOPage(groupRepository.findByParentGroupIsNull(pageable));
	}
}