package com.study.Main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.Main.Dto.GroupRequestDTO;
import com.study.Main.Dto.GroupResponseDTO;
import com.study.Main.Service.GroupService;
import com.study.Main.response.ApiResponsePattern;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

	private final GroupService groupService;

	// D - depends on interface
	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@PostMapping
	public ResponseEntity<ApiResponsePattern<GroupResponseDTO>> addGroup(@Valid @RequestBody GroupRequestDTO request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponsePattern.success(groupService.addGroup(request), "Group added successfully"));
	}

	@GetMapping("/{groupId}")
	public ResponseEntity<ApiResponsePattern<GroupResponseDTO>> getGroupById(@PathVariable Long groupId) {
		return ResponseEntity.ok(ApiResponsePattern.success(groupService.getGroupById(groupId)));
	}

	@GetMapping
	public ResponseEntity<ApiResponsePattern<Page<Map<String, Object>>>> getGroups(
			@RequestParam(required = true) Long companyId, @RequestParam(required = false) List<String> select,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {

		return ResponseEntity.ok(ApiResponsePattern.success(groupService.getGroups(companyId, select, page, size)));
	}

//	@GetMapping("/parent/{parentGroupId}")
//	public ResponseEntity<ApiResponsePattern<Page<GroupResponseDTO>>> getGroupsByParentId(
//			@PathVariable Long parentGroupId, @RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "20") int size) {
//		return ResponseEntity
//				.ok(ApiResponsePattern.success(groupService.getGroupsByParentId(parentGroupId, page, size)));
//	}

	@GetMapping("/type/{groupType}")
	public ResponseEntity<ApiResponsePattern<Page<GroupResponseDTO>>> getGroupsByType(@PathVariable String groupType,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
		return ResponseEntity.ok(ApiResponsePattern.success(groupService.getGroupsByType(groupType, page, size)));
	}

	@GetMapping("/roots")
	public ResponseEntity<ApiResponsePattern<Page<GroupResponseDTO>>> getRootGroups(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
		return ResponseEntity.ok(ApiResponsePattern.success(groupService.getRootGroups(page, size)));
	}

	@DeleteMapping("/{groupId}")
	public ResponseEntity<ApiResponsePattern<String>> deleteGroup(@PathVariable Long groupId) {
		groupService.deleteGroup(groupId);
		return ResponseEntity.ok(ApiResponsePattern.success("Group deleted successfully"));
	}

}
