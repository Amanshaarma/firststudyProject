package com.tms.Main.Service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tms.Main.util.ValidColumns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tms.Main.Dto.GroupRequestDTO;
import com.tms.Main.Dto.GroupResponseDTO;
import com.tms.Main.Expection.BusinessRuleViolationException;
import com.tms.Main.Expection.DuplicateResourceException;
import com.tms.Main.Expection.ResourceNotFoundException;
import com.tms.Main.Mapper.GroupMapper;
import com.tms.Main.Model.CompanyProfiles;
import com.tms.Main.Model.GlobalGroup;
import com.tms.Main.Model.Group;
import com.tms.Main.Repository.CompanyRepository;
import com.tms.Main.Repository.GlobalGroupRepository;
import com.tms.Main.Repository.GroupRepository;
import com.tms.Main.Service.GroupService;

import jakarta.transaction.Transactional;

@Service
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepository;
	private final CompanyRepository companyProfileRepository;
	private final GroupMapper groupMapper;
	private final GlobalGroupRepository globalGroupRepository;
    private final ValidColumns validColumns;

	// D - Constructor injection
	public GroupServiceImpl(GroupRepository groupRepository, CompanyRepository companyProfileRepository,
                            GroupMapper groupMapper, GlobalGroupRepository globalGroupRepository, ValidColumns validColumns) {
		this.groupRepository = groupRepository;
		this.companyProfileRepository = companyProfileRepository;
		this.groupMapper = groupMapper;
		this.globalGroupRepository = globalGroupRepository;
        this.validColumns = validColumns;
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
	public Map<String, Object> getGroupById(Long groupId,List<String> select) {
		GroupResponseDTO groupDto = groupMapper.toDTO(groupRepository.findById(groupId)
				.orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId)));
        Set<String> fields = validColumns.resolveFields(select, "LEDGER_COLUMNS");

        return groupMapper.toProjectedMap(groupDto, fields);
	}

	@Override
	public Page<Map<String, Object>> getGroups(Long companyId, List<String> select, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Group> groupPage = (companyId != null) ? groupRepository.findByCompanyProfileCompanyId(companyId, pageable)
				: groupRepository.findAll(pageable);

		Set<String> fields = validColumns.resolveFields(select,"GROUP_COLUMNS");

        System.out.println(groupPage);

		return groupMapper.toDTOPageProjected(groupPage, fields); // ✅ single call
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
    @Transactional
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
    @Transactional
    public GroupResponseDTO     updateGroup(GroupRequestDTO request,Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + groupId));
        GlobalGroup globalGroup = globalGroupRepository.findById(request.getGlobalGroupId()).orElseThrow(
                () -> new ResourceNotFoundException("Group not found with id: " + groupId));
        Group parentGroup = null;
        if(request.getParentGroupId() != null && request.getParentGroupId() > 0)
        {
            parentGroup = groupRepository.findById(request.getParentGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Group not found with id: " + request.getParentGroupId()));
        }
        group.setGroupName(request.getGroupName() != null && !request.getGroupName().trim().isEmpty() ? request.getGroupName(): group.getGroupName());
        group.setGroupType(request.getGroupType() != null ?request.getGroupType() : group.getGroupType());
        group.setGlobalGroup(globalGroup != null ? globalGroup : group.getGlobalGroup());
        if(parentGroup != null)
            group.setParentGroup(parentGroup);
        return groupMapper.toDTO(groupRepository.save(group));
    }

    @Override
	public Page<GroupResponseDTO> getGroupsByParentId(Long parentGroupId, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}
}