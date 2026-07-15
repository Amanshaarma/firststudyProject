package com.tms.Main.controller;

import com.tms.Main.Dto.VehicleRequest;
import com.tms.Main.Service.impl.VehicleServiceImpl;
import com.tms.Main.response.ApiResponsePattern;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Vehicle Management REST API
 *
 * Exactly 5 endpoints, matching the spec table:
 *   GET    /api/vehicles                → companyId required; ownerLedgerId & select optional
 *   GET    /api/vehicles/{vehicleId}    → select optional
 *   POST   /api/vehicles
 *   PUT    /api/vehicles/{vehicleId}
 *   DELETE /api/vehicles/{vehicleId}
 *
 * IMPORTANT: There is only ONE @GetMapping per path. All param combinations
 * (companyId alone / companyId+select / companyId+ownerLedgerId / all three)
 * are handled inside the single method via optional @RequestParam - this is
 * required because Spring cannot route multiple methods mapped to the same
 * path based on which optional params are present ("ambiguous mapping").
 */
@Slf4j
@RestController
@RequestMapping("/api/vehicles")
@Validated
public class VehicleController {

    private VehicleServiceImpl vehicleService;

    public VehicleController(VehicleServiceImpl vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * GET /api/vehicles?companyId=1
     * GET /api/vehicles?companyId=1&select=vehicleNo,vehicleType
     * GET /api/vehicles?companyId=1&ownerLedgerId=5
     * GET /api/vehicles?companyId=1&ownerLedgerId=5&select=vehicleNo,vehicleType
     */
    @GetMapping
    public ResponseEntity<ApiResponsePattern<Page<Map<String, Object>>>> getVehicles(
            @RequestParam Long companyId,
            @RequestParam(required = false) Long ownerLedgerId,
            @RequestParam(required = false) List<String> select,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/vehicles companyId={} ownerLedgerId={} select={} page={} size={}",
                companyId, ownerLedgerId, select, page, size);

        Page<Map<String, Object>> result = vehicleService.getVehicles(companyId, ownerLedgerId, select, page, size);
        return ResponseEntity.ok(ApiResponsePattern.success(result));
    }

    /**
     * GET /api/vehicles/{vehicleId}                 -> full object
     * GET /api/vehicles/{vehicleId}?select=vehicleNo -> selected columns only
     */
    @GetMapping("/{vehicleId}")
    public ResponseEntity<ApiResponsePattern<Map<String, Object>>> getVehicleById(
            @PathVariable Long vehicleId,
            @RequestParam(required = false) List<String> select) {

        log.info("GET /api/vehicles/{} select={}", vehicleId, select);

        Map<String, Object> result = vehicleService.getVehicleById(vehicleId, select);
        return ResponseEntity.ok(ApiResponsePattern.success(result));
    }

    /**
     * POST /api/vehicles
     * Validates: vehicleNo uniqueness within companyId; ownerLedgerId (if given)
     * must belong to the same companyId.
     */
    @PostMapping
    public ResponseEntity<ApiResponsePattern<Map<String, Object>>> createVehicle(@Valid @RequestBody VehicleRequest request) {
        log.info("POST /api/vehicles companyId={} vehicleNo={}", request.getCompanyId(), request.getVehicleNo());
        Map<String, Object> result = vehicleService.createVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponsePattern.success(result));
    }

    /**
     * PUT /api/vehicles/{vehicleId}
     * Same validations as POST; companyId is immutable once the vehicle exists.
     */
    @PutMapping("/{vehicleId}")
    public ResponseEntity<ApiResponsePattern<Map<String, Object>>> updateVehicle(
            @PathVariable Long vehicleId,
            @Valid @RequestBody VehicleRequest request) {

        log.info("PUT /api/vehicles/{} vehicleNo={}", vehicleId, request.getVehicleNo());
        Map<String, Object> result = vehicleService.updateVehicle(vehicleId, request);
        return ResponseEntity.ok(ApiResponsePattern.success(result));
    }

    /**
     * DELETE /api/vehicles/{vehicleId}
     * Rejected (409) if any trip references this vehicle.
     */
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<ApiResponsePattern<Object>> deleteVehicle(@PathVariable Long vehicleId) {
        log.info("DELETE /api/vehicles/{}", vehicleId);
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.ok(ApiResponsePattern.success(null,"vehicle is deleted "));
    }
}