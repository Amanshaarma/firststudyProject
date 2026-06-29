package com.study.Main.Service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.study.Main.Dto.GroupRequestDTO;
import com.study.Main.Dto.GroupResponseDTO;
import com.study.Main.Expection.BusinessRuleViolationException;
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

		// 2. ✅ Duplicate group name check within same company
		if (groupRepository.existsByGroupNameAndCompanyProfileCompanyId(request.getGroupName(),
				request.getCompanyId())) {
			throw new DuplicateResourceException("Group already exists: " + request.getGroupName());
		}

		// 3. Map RequestDTO → Entity
		Group group = groupMapper.toEntity(request);
		group.setCompanyProfile(companyProfile);

		// 4. ✅ Parent group — check exists AND belongs to same company
		if (request.getParentGroupId() != null  && request.getParentGroupId() > 0) {						

			// Check parent exists in same company
			if (!groupRepository.existsByGroupIdAndCompanyProfileCompanyId(request.getParentGroupId(),
					request.getCompanyId())) {
				throw new ResourceNotFoundException("Parent group not found in company id: " + request.getCompanyId());
			}

			Group parentGroup = groupRepository.findById(request.getParentGroupId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Parent group not found with id: " + request.getParentGroupId()));

			group.setParentGroup(parentGroup);
		}

		// 5. ✅ GlobalGroup — check exists in GlobalGroups table
		if (request.getGlobalGroupId() != null) {
			GlobalGroup globalGroup = globalGroupRepository.findById(request.getGlobalGroupId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Global group not found with id: " + request.getGlobalGroupId()));
			group.setGlobalGroup(globalGroup);
		}

		// 6. Save & return
		return groupMapper.toDTO(groupRepository.save(group));
	}

	@Override
	public GroupResponseDTO getGroupById(Long groupId) {
		return groupMapper.toDTO(groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId)));
	}

	@Override
	public Page<Map<String, Object>> getGroups(Long companyId, List<String> select, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Group> groupPage = (companyId != null) ? groupRepository.findByCompanyProfileCompanyId(companyId, pageable)
				: groupRepository.findAll(pageable);

		Set<String> fields = resolveFields(select);

		return groupMapper.toDTOPageProjected(groupPage, fields); // ✅ single call
	}

	private Set<String> resolveFields(List<String> select) {
		if (select == null || select.isEmpty())
			return Set.of(); // empty = all fields

		Set<String> allowed = Set.of("groupId", "groupName", "groupType", "companyId", "parentGroupId", "globalGroupId",
				"createdAt", "updatedAt");

		Set<String> requested = select.stream().map(s -> Character.toLowerCase(s.charAt(0)) + s.substring(1)) // GroupName
																												// →
																												// groupName
				.filter(allowed::contains).collect(Collectors.toCollection(LinkedHashSet::new));

		if (requested.isEmpty())
			throw new IllegalArgumentException("No valid fields in 'select'. Allowed: " + allowed);

		return requested;
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

	@Override
	public void deleteGroup(Long groupId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));

		boolean hasChildren = groupRepository.existsByParentGroup_GroupId(groupId);
		if (hasChildren) {
			throw new BusinessRuleViolationException(
					"Cannot delete group '" + group.getGroupName() + "' because it has child groups.");
		}

		groupRepository.delete(group);
	}

	@Override
	public Page<GroupResponseDTO> getGroupsByParentId(Long parentGroupId, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}
}