package com.study.Main.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.Main.Dto.CompanyRequestDTO;
import com.study.Main.Dto.CompanyResponseDTO;
import com.study.Main.Service.CompanyService;
import com.study.Main.response.ApiResponsePattern;

import jakarta.validation.Valid;

//controller/CompanyController.java
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

 private final CompanyService companyService;

 // D - depends on interface
 public CompanyController(CompanyService companyService) {
     this.companyService = companyService;
 }

 @PostMapping
 public ResponseEntity<ApiResponsePattern<CompanyResponseDTO>> addCompany(
         @Valid @RequestBody CompanyRequestDTO request) {

     CompanyResponseDTO response = companyService.addCompany(request);
     return ResponseEntity
             .status(HttpStatus.CREATED)
             .body(ApiResponsePattern.success(response, "Company added successfully"));
 }

 @GetMapping("/{companyId}")
 public ResponseEntity<ApiResponsePattern<CompanyResponseDTO>> getCompanyById(
         @PathVariable Long companyId) {

     return ResponseEntity.ok(
             ApiResponsePattern.success(companyService.getCompanyById(companyId)));
 }

 @GetMapping("/user/{userId}")
 public ResponseEntity<ApiResponsePattern<List<CompanyResponseDTO>>> getCompaniesByUserId(
         @PathVariable Long userId) {

     return ResponseEntity.ok(
             ApiResponsePattern.success(companyService.getCompaniesByUserId(userId)));
 }
}