package com.tms.Main.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tms.Main.Dto.GlobalGroupRequestDTO;
import com.tms.Main.Dto.GlobalGroupResponseDTO;
import com.tms.Main.Service.GlobalGroupService;
import com.tms.Main.response.ApiResponsePattern;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/global-groups")
public class GlobalGroupController {

 private final GlobalGroupService globalGroupService;

 // D - depends on interface
 public GlobalGroupController(GlobalGroupService globalGroupService) {
     this.globalGroupService = globalGroupService;
 }

 @PostMapping
 public ResponseEntity<ApiResponsePattern<GlobalGroupResponseDTO>> addGlobalGroup(
         @Valid @RequestBody GlobalGroupRequestDTO request) {
     return ResponseEntity
             .status(HttpStatus.CREATED)
             .body(ApiResponsePattern.success(
                     globalGroupService.addGlobalGroup(request),
                     "Global group added successfully"));
 }

 @GetMapping("/{globalGroupId}")
 public ResponseEntity<ApiResponsePattern<GlobalGroupResponseDTO>> getGlobalGroupById(
         @PathVariable Long globalGroupId) {
     return ResponseEntity.ok(
             ApiResponsePattern.success(
                     globalGroupService.getGlobalGroupById(globalGroupId)));
 }

 @GetMapping
 public ResponseEntity<ApiResponsePattern<Page<GlobalGroupResponseDTO>>> getAllGlobalGroups(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "20") int size) {
     return ResponseEntity.ok(
             ApiResponsePattern.success(globalGroupService.getAllGlobalGroups(page, size)));
 }

 @GetMapping("/search")
 public ResponseEntity<ApiResponsePattern<Page<GlobalGroupResponseDTO>>> searchByGroupName(
         @RequestParam String groupName,
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "20") int size) {
     return ResponseEntity.ok(
             ApiResponsePattern.success(
                     globalGroupService.searchByGroupName(groupName, page, size)));
 }
}