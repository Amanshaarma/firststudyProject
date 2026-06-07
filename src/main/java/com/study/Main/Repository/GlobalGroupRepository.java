package com.study.Main.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.study.Main.Model.GlobalGroup;

public interface GlobalGroupRepository extends JpaRepository<GlobalGroup, Long> {

	// Duplicate check
	boolean existsByGroupName(String groupName);

	// Search by name — paginated
	Page<GlobalGroup> findByGroupNameContainingIgnoreCase(String groupName, Pageable pageable);

	// All — paginated
	Page<GlobalGroup> findAll(Pageable pageable);
}
