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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.Main.Dto.LedgerRequestDTO;
import com.study.Main.Dto.LedgerResponseDTO;
import com.study.Main.Service.LedgerService;
import com.study.Main.enumData.GroupType;
import com.study.Main.response.ApiResponsePattern;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ledgers")
public class LedgerController {

 private final LedgerService ledgerService;

 public LedgerController(LedgerService ledgerService) {
     this.ledgerService = ledgerService;
 }

 // ✅ POST
 @PostMapping
 public ResponseEntity<ApiResponsePattern<LedgerResponseDTO>> addLedger( 
         @Valid @RequestBody LedgerRequestDTO request) {
     return ResponseEntity
             .status(HttpStatus.CREATED)
             .body(ApiResponsePattern.success(
                     ledgerService.addLedger(request),
                     "Ledger added successfully"));
 }


//GET All
 @GetMapping
 public ResponseEntity<ApiResponsePattern<Page<Map<String, Object>>>> getAllLedgers(
         @RequestParam(required = true) Long companyId,
         @RequestParam(required = false) List<String> select,   // ✅ column names
         @RequestParam(required = false) List<GroupType> groupType,
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "20") int size) {

     return ResponseEntity.ok(
    		 ApiResponsePattern.success(
                     ledgerService.getAllLedgers(
                             companyId, select, groupType, page, size)));
 }

 // GET by ID
 @GetMapping("/{ledgerId}")
 public ResponseEntity<ApiResponsePattern<Map<String, Object>>> getLedgerById(
         @PathVariable Long ledgerId,
         @RequestParam(required = false) List<String> select) {  // ✅ column names

     return ResponseEntity.ok(
    		 ApiResponsePattern.success(
                     ledgerService.getLedgerById(ledgerId, select)));
 }

 // ✅ UPDATE
 @PutMapping("/{ledgerId}")
 public ResponseEntity<ApiResponsePattern<LedgerResponseDTO>> updateLedger(
         @PathVariable Long ledgerId,
         @Valid @RequestBody LedgerRequestDTO request) {
     return ResponseEntity.ok(
    		 ApiResponsePattern.success(
                     ledgerService.updateLedger(ledgerId, request),
                     "Ledger updated successfully"));
 }

 // ✅ DELETE
 @DeleteMapping("/{ledgerId}")
 public ResponseEntity<ApiResponsePattern<Object>> deleteLedger(
         @PathVariable Long ledgerId) {
     ledgerService.deleteLedger(ledgerId);
     return ResponseEntity.ok(
    		 ApiResponsePattern.success(null, "Ledger deleted successfully"));
 }
}
