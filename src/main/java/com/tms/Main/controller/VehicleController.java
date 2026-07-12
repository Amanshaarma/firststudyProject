package com.study.Main.controller;

package com.tms.controller;

import com.tms.dto.VehicleRequest;
import com.tms.dto.VehicleResponse;
import com.tms.exception.ResourceNotFoundException;
import com.tms.exception.DuplicateResourceException;
import com.tms.exception.ValidationException;
import com.tms.exception.ConstraintViolationException;
import com.tms.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Vehicle Management REST API Controller
 *
 * Endpoints:
 * - GET    /api/vehicles                      → Get all vehicles with filters
 * - GET    /api/vehicles/{vehicleId}          → Get specific vehicle
 * - POST   /api/vehicles                      → Create new vehicle
 * - PUT    /api/vehicles/{vehicleId}          → Update vehicle
 * - DELETE /api/vehicles/{vehicleId}          → Delete vehicle
 */
@Slf4j
@RestController
@RequestMapping("/api/vehicles")
@Validated
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // =========================================================================
    // GET - ALL VEHICLES
    // =========================================================================

    /**
     * GET /api/vehicles?companyId=1
     * Return all vehicles for a company with all columns
     *
     * @param companyId Required - Company ID to filter vehicles
     * @return List of all vehicles for the company
     * @throws ResourceNotFoundException If company not found
     * @throws ValidationException If companyId is invalid
     */
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles(
            @RequestParam(name = "companyId") @NotNull(message = "companyId is required") Long companyId) {

        log.info("GET /api/vehicles - companyId={}", companyId);
        List<VehicleResponse> vehicles = vehicleService.getAllVehiclesByCompany(companyId);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * GET /api/vehicles?companyId=1&select=vehicleNo,vehicleType
     * Return all vehicles with selected columns
     *
     * @param companyId Required - Company ID
     * @param select Optional - Comma-separated column names (e.g., vehicleNo,vehicleType,ownerLedgerId)
     * @return List of vehicles with selected columns
     */
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehiclesWithSelect(
            @RequestParam(name = "companyId") @NotNull(message = "companyId is required") Long companyId,
            @RequestParam(name = "select", required = false) String select) {

        log.info("GET /api/vehicles - companyId={}, select={}", companyId, select);

        if (select != null && !select.isEmpty()) {
            List<VehicleResponse> vehicles = vehicleService.getAllVehiclesByCompanyWithSelect(companyId, select);
            return ResponseEntity.ok(vehicles);
        }

        return getAllVehicles(companyId);
    }

    /**
     * GET /api/vehicles?companyId=1&ownerLedgerId=5
     * Return all vehicles where companyId AND ownerLedgerId match (all columns)
     *
     * @param companyId Required - Company ID
     * @param ownerLedgerId Required - Owner ledger ID to filter
     * @return List of vehicles matching both filters
     */
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehiclesByCompanyAndOwner(
            @RequestParam(name = "companyId") @NotNull(message = "companyId is required") Long companyId,
            @RequestParam(name = "ownerLedgerId") @NotNull(message = "ownerLedgerId is required") Long ownerLedgerId) {

        log.info("GET /api/vehicles - companyId={}, ownerLedgerId={}", companyId, ownerLedgerId);
        List<VehicleResponse> vehicles = vehicleService.getVehiclesByCompanyAndOwner(companyId, ownerLedgerId);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * GET /api/vehicles?companyId=1&ownerLedgerId=5&select=vehicleNo,vehicleType
     * Return vehicles matching companyId AND ownerLedgerId with selected columns
     *
     * @param companyId Required - Company ID
     * @param ownerLedgerId Required - Owner ledger ID
     * @param select Optional - Comma-separated column names
     * @return List of vehicles with selected columns
     */
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getVehiclesByCompanyAndOwnerWithSelect(
            @RequestParam(name = "companyId") @NotNull(message = "companyId is required") Long companyId,
            @RequestParam(name = "ownerLedgerId") @NotNull(message = "ownerLedgerId is required") Long ownerLedgerId,
            @RequestParam(name = "select", required = false) String select) {

        log.info("GET /api/vehicles - companyId={}, ownerLedgerId={}, select={}",
                companyId, ownerLedgerId, select);

        if (select != null && !select.isEmpty()) {
            List<VehicleResponse> vehicles = vehicleService
                    .getVehiclesByCompanyAndOwnerWithSelect(companyId, ownerLedgerId, select);
            return ResponseEntity.ok(vehicles);
        }

        return getVehiclesByCompanyAndOwner(companyId, ownerLedgerId);
    }

    // =========================================================================
    // GET - BY VEHICLE ID
    // =========================================================================

    /**
     * GET /api/vehicles/{vehicleId}
     * Return specific vehicle with all columns
     *
     * @param vehicleId Path variable - Vehicle ID to fetch
     * @return Specific vehicle details
     * @throws ResourceNotFoundException If vehicle not found
     */
    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleResponse> getVehicleById(
            @PathVariable @NotNull(message = "vehicleId is required") Long vehicleId) {

        log.info("GET /api/vehicles/{} - Fetching vehicle by ID", vehicleId);
        VehicleResponse vehicle = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.ok(vehicle);
    }

    /**
     * GET /api/vehicles/{vehicleId}?select=vehicleNo,vehicleType
     * Return specific vehicle with selected columns
     *
     * @param vehicleId Path variable - Vehicle ID
     * @param select Optional - Comma-separated column names
     * @return Vehicle with selected columns
     */
    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleResponse> getVehicleByIdWithSelect(
            @PathVariable @NotNull(message = "vehicleId is required") Long vehicleId,
            @RequestParam(name = "select", required = false) String select) {

        log.info("GET /api/vehicles/{} - select={}", vehicleId, select);

        if (select != null && !select.isEmpty()) {
            VehicleResponse vehicle = vehicleService.getVehicleByIdWithSelect(vehicleId, select);
            return ResponseEntity.ok(vehicle);
        }

        return getVehicleById(vehicleId);
    }

    // =========================================================================
    // POST - CREATE VEHICLE
    // =========================================================================

    /**
     * POST /api/vehicles
     * Create new vehicle
     *
     * Validations:
     * - Check vehicleNo doesn't already exist for the same companyId
     * - If ownerLedgerId provided, verify it exists in the same company
     *
     * @param request VehicleRequest DTO with vehicle details
     * @return Created vehicle response
     * @throws DuplicateResourceException If vehicleNo already exists for company
     * @throws ResourceNotFoundException If ownerLedgerId not found in company
     * @throws ValidationException If request data invalid
     */
    @PostMapping
    public ResponseEntity<VehicleResponse> createVehicle(
            @Valid @RequestBody VehicleRequest request) {

        log.info("POST /api/vehicles - Creating vehicle: companyId={}, vehicleNo={}",
                request.getCompanyId(), request.getVehicleNo());

        VehicleResponse vehicle = vehicleService.createVehicle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicle);
    }

    // =========================================================================
    // PUT - UPDATE VEHICLE
    // =========================================================================

    /**
     * PUT /api/vehicles/{vehicleId}
     * Update existing vehicle
     *
     * Validations:
     * - Same as POST (check vehicleNo uniqueness within company, ownerLedgerId validity)
     * - Cannot update companyId (tenant isolation)
     *
     * @param vehicleId Path variable - Vehicle ID to update
     * @param request VehicleRequest DTO with updated details
     * @return Updated vehicle response
     * @throws ResourceNotFoundException If vehicle not found
     * @throws DuplicateResourceException If vehicleNo conflicts with another vehicle
     * @throws ValidationException If ownerLedgerId invalid
     */
    @PutMapping("/{vehicleId}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @PathVariable @NotNull(message = "vehicleId is required") Long vehicleId,
            @Valid @RequestBody VehicleRequest request) {

        log.info("PUT /api/vehicles/{} - Updating vehicle: vehicleNo={}",
                vehicleId, request.getVehicleNo());

        VehicleResponse vehicle = vehicleService.updateVehicle(vehicleId, request);
        return ResponseEntity.ok(vehicle);
    }

    // =========================================================================
    // DELETE - DELETE VEHICLE
    // =========================================================================

    /**
     * DELETE /api/vehicles/{vehicleId}
     * Delete vehicle
     *
     * Constraints:
     * - Can only delete if NO dependent records exist in trips table
     * - If trips reference this vehicle, deletion will be rejected
     *
     * @param vehicleId Path variable - Vehicle ID to delete
     * @return Success response (204 No Content)
     * @throws ResourceNotFoundException If vehicle not found
     * @throws ConstraintViolationException If dependent records exist (trips)
     */
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable @NotNull(message = "vehicleId is required") Long vehicleId) {

        log.info("DELETE /api/vehicles/{} - Deleting vehicle", vehicleId);
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.noContent().build();
    }
}
