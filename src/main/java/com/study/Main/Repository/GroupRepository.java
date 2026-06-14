package com.study.Main.Repository;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.Main.Model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	// Find by company — paginated
	Page<Group> findByCompanyProfileCompanyId(Long companyId, Pageable pageable);

	// Find by parent group — paginated
	Page<Group> findByParentGroupGroupId(Long parentGroupId, Pageable pageable);

	// Find by group type — paginated
	Page<Group> findByGroupType(String groupType, Pageable pageable);

	// Find root groups (no parent) — paginated
	Page<Group> findByParentGroupIsNull(Pageable pageable);

	boolean existsByParentGroup_GroupId(Long groupId);

	// Duplicate check
	boolean existsByGroupNameAndCompanyProfileCompanyId(String groupName, Long companyId);
	
	// repository/GroupRepository.java

	// Add this method
	boolean existsByGroupIdAndCompanyProfileCompanyId(Long groupId, Long companyId);
}
