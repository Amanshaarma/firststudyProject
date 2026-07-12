package com.tms.Main.Service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tms.Main.Dto.GlobalGroupRequestDTO;
import com.tms.Main.Dto.GlobalGroupResponseDTO;
import com.tms.Main.Expection.DuplicateResourceException;
import com.tms.Main.Expection.ResourceNotFoundException;
import com.tms.Main.Mapper.GlobalGroupMapper;
import com.tms.Main.Model.GlobalGroup;
import com.tms.Main.Repository.GlobalGroupRepository;
import com.tms.Main.Service.GlobalGroupService;

import jakarta.transaction.Transactional;

//service/impl/GlobalGroupServiceImpl.java
@Service
public class GlobalGroupServiceImpl implements GlobalGroupService {

	private final GlobalGroupRepository globalGroupRepository;
	private final GlobalGroupMapper globalGroupMapper;

	// D - Constructor injection
	public GlobalGroupServiceImpl(GlobalGroupRepository globalGroupRepository, GlobalGroupMapper globalGroupMapper) {
		this.globalGroupRepository = globalGroupRepository;
		this.globalGroupMapper = globalGroupMapper;
	}

	@Override
	@Transactional
	public GlobalGroupResponseDTO addGlobalGroup(GlobalGroupRequestDTO request) {

		// Duplicate check
		if (globalGroupRepository.existsByGroupName(request.getGroupName())) {
			throw new DuplicateResourceException("Global group already exists: " + request.getGroupName());
		}

		GlobalGroup globalGroup = globalGroupMapper.toEntity(request);
		return globalGroupMapper.toDTO(globalGroupRepository.save(globalGroup));
	}

	@Override
	public GlobalGroupResponseDTO getGlobalGroupById(Long globalGroupId) {
		return globalGroupMapper.toDTO(globalGroupRepository.findById(globalGroupId)
				.orElseThrow(() -> new ResourceNotFoundException("Global group not found with id: " + globalGroupId)));
	}

	@Override
	public Page<GlobalGroupResponseDTO> getAllGlobalGroups(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("groupName").ascending());
		return globalGroupMapper.toDTOPage(globalGroupRepository.findAll(pageable));
	}

	@Override
	public Page<GlobalGroupResponseDTO> searchByGroupName(String groupName, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("groupName").ascending());
		return globalGroupMapper
				.toDTOPage(globalGroupRepository.findByGroupNameContainingIgnoreCase(groupName, pageable));
	}
}