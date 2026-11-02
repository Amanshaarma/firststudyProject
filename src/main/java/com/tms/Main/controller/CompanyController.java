package com.tms.Main.controller;

import com.tms.Main.Dto.CompanyRequestDTO;
import com.tms.Main.Dto.CompanyResponseDTO;
import com.tms.Main.Service.CompanyService;
import com.tms.Main.response.ApiResponsePattern;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
             ApiResponsePattern.success(companyService.getCompanyByIdByDto(companyId)));
 }

 @GetMapping("/user/{userId}")
 public ResponseEntity<ApiResponsePattern<List<CompanyResponseDTO>>> getCompaniesByUserId(
         @PathVariable Long userId) {

     return ResponseEntity.ok(
             ApiResponsePattern.success(companyService.getCompaniesByUserId(userId)));
 }
}