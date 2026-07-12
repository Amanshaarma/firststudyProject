package com.tms.Main.Service.impl;

import com.tms.Main.Dto.VehicleRequest;
import com.tms.Main.Expection.ConstraintViolationException;
import com.tms.Main.Expection.DuplicateResourceException;
import com.tms.Main.Model.CompanyProfiles;
import com.tms.Main.Model.Vehicle;
import com.tms.Main.Model.Ledger;
import com.tms.Main.Repository.CompanyRepository;
import com.tms.Main.Repository.LedgerRepository;
import com.tms.Main.Repository.VehicleRepository;
import com.tms.Main.util.ValidColumns;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tms.Main.Expection.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Vehicle Service - Business Logic Layer
 * <p>
 * Fixes applied vs. earlier version:
 * 1. Company existence is checked against CompanyRepository (company_profiles),
 * NOT against the vehicles table. A company with zero vehicles is still valid.
 * 2. "select" resolution: null/empty -> all columns. Comma list -> keep only
 * recognized columns; if the list has zero recognized columns -> 400 error;
 * if it has 1+ recognized columns -> return only those, silently drop the rest.
 * 3. Response shape for "select" is a dynamic Map (LinkedHashMap) containing only
 * the resolved keys, not a fixed DTO with nulls.
 */
@Slf4j
@Service
public class VehicleServiceImpl {
    private ValidColumns validColumns;

    private VehicleRepository vehicleRepository;


    private CompanyRepository companyRepository;


    private LedgerRepository ledgerRepository;

//    @Autowired
//    private TripRepository tripRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository,  ValidColumns validColumns,CompanyRepository companyRepository, LedgerRepository ledgerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.companyRepository = companyRepository;
        this.ledgerRepository = ledgerRepository;
        this.validColumns =  validColumns;
//        this.tripRepository = tripRepository;
    }

    // =========================================================================
    // GET (All) - single consolidated endpoint backs this
    //   companyId          -> required
    //   ownerLedgerId       -> optional
    //   select              -> optional
    // =========================================================================
    public List<Map<String, Object>> getVehicles(Long companyId, Long ownerLedgerId, List<String> select) {
        validateCompanyExists(companyId);

        List<Vehicle> vehicles;
        if (ownerLedgerId != null) {
            validateLedgerBelongsToCompany(ownerLedgerId, companyId);
            vehicles = vehicleRepository.findByCompanyIdAndOwnerLedgerId(companyId, ownerLedgerId);
        } else {
            vehicles = vehicleRepository.findByCompanyId(companyId);
        }

        Set<String> resolvedColumns = validColumns.resolveFields(select,"VEHICLE_COLUMNS"); // null = all columns
        return vehicles.stream()
                .map(v -> toMap(v, resolvedColumns))
                .collect(Collectors.toList());
    }

    // =========================================================================
    // GET (by vehicleId) - single consolidated endpoint
    //   select -> optional
    // =========================================================================
    public Map<String, Object> getVehicleById(Long vehicleId, List<String> select) {
        Vehicle vehicle = findVehicleOrThrow(vehicleId);
        Set<String> resolvedColumns =  validColumns.resolveFields(select,"VEHICLE_COLUMNS");
        return toMap(vehicle, resolvedColumns);
    }

    // =========================================================================
    // POST - Create
    // =========================================================================
    @Transactional
    public Map<String, Object> createVehicle(VehicleRequest request) {
        log.info("Creating vehicle: companyId={}, vehicleNo={}", request.getCompanyId(), request.getVehicleNo());

        validateCompanyExists(request.getCompanyId());
        checkVehicleNoDuplicate(request.getCompanyId(), request.getVehicleNo());

        if (request.getOwnerLedgerId() != null) {
            validateLedgerBelongsToCompany(request.getOwnerLedgerId(), request.getCompanyId());
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setCompanyId(request.getCompanyId());
        vehicle.setVehicleNo(request.getVehicleNo().trim());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setOwnerLedgerId(request.getOwnerLedgerId());

        Vehicle saved = vehicleRepository.save(vehicle);
        log.info("Vehicle created: vehicleId={}", saved.getVehicleId());

        return toMap(saved, null); // full object on create
    }

    // =========================================================================
    // UPDATE - same conditions as POST
    // =========================================================================
    @Transactional
    public Map<String, Object> updateVehicle(Long vehicleId, VehicleRequest request) {
        log.info("Updating vehicle: vehicleId={} ", vehicleId);

        Vehicle vehicle = findVehicleOrThrow(vehicleId);

        validateCompanyExists(request.getCompanyId());

        if (!vehicle.getCompanyId().equals(request.getCompanyId())) {
            throw new ValidationException(
                    "Cannot change companyId for an existing vehicle" +
                            " COMPANY_ID_IMMUTABLE"
            );
        }

        // Only re-check duplicate if vehicleNo actually changed (case-insensitive compare)
        if (!vehicle.getVehicleNo().equalsIgnoreCase(request.getVehicleNo().trim())) {
            checkVehicleNoDuplicate(request.getCompanyId(), request.getVehicleNo());
        }

        if (request.getOwnerLedgerId() != null) {
            validateLedgerBelongsToCompany(request.getOwnerLedgerId(), request.getCompanyId());
        }

        vehicle.setVehicleNo(request.getVehicleNo().trim());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setOwnerLedgerId(request.getOwnerLedgerId());

        Vehicle updated = vehicleRepository.save(vehicle);
        log.info("Vehicle updated: vehicleId={} ", vehicleId);

        return toMap(updated, null);
    }

    // =========================================================================
    // DELETE - only if no trips reference this vehicle
    // =========================================================================
//    @Transactional
//    public void deleteVehicle(Long vehicleId) {
//        log.info("Deleting vehicle: vehicleId={}", vehicleId);
//
//        Vehicle vehicle = findVehicleOrThrow(vehicleId);
//
//        long tripCount = tripRepository.countByVehicleId(vehicleId);
//        if (tripCount > 0) {
//            throw new ConstraintViolationException(
//                    "Cannot delete vehicle: " + tripCount + " trip(s) reference this vehicle" +
//                            "VEHICLE_HAS_DEPENDENT_TRIPS" +
//                            "trips", "409"
//            );
//        }
//
//        vehicleRepository.delete(vehicle);
//        log.info("Vehicle deleted: vehicleId={}", vehicleId);
//    }

    // =========================================================================
    // HELPERS
    // =========================================================================

    private Vehicle findVehicleOrThrow(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehicle not found with ID: " + vehicleId + " VEHICLE_NOT_FOUND"));
    }

    /**
     * Checks against company_profiles - a company with 0 vehicles is still valid.
     */
    private void validateCompanyExists(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new ValidationException("companyId must be a positive number" + " INVALID_COMPANY_ID");
        }
        if (!companyRepository.existsById(companyId)) {
            throw new ResourceNotFoundException("Company not found with ID: " + companyId + " COMPANY_NOT_FOUND");
        }
    }

    /**
     * ownerLedgerId must exist AND belong to the same companyId (tenant isolation).
     */
    private void validateLedgerBelongsToCompany(Long ledgerId, Long companyId) {
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ledger not found with ID: " + ledgerId + " LEDGER_NOT_FOUND"));

        if (!ledger.getCompanyProfile().getCompanyId().equals(companyId)) {
            throw new ValidationException(
                    "ownerLedgerId " + ledgerId + " does not belong to companyId " + companyId +
                            " LEDGER_COMPANY_MISMATCH"
            );
        }
    }

    private void checkVehicleNoDuplicate(Long companyId, String vehicleNo) {
        if (vehicleNo == null || vehicleNo.trim().isEmpty()) {
            throw new ValidationException("vehicleNo cannot be null or empty" + " VEHICLE_NO_REQUIRED");
        }
        boolean exists = vehicleRepository
                .findByCompanyIdAndVehicleNoIgnoreCase(companyId, vehicleNo.trim())
                .isPresent();
        if (exists) {
            throw new DuplicateResourceException(
                    "Vehicle with number '" + vehicleNo + "' already exists for this company" +
                            " VEHICLE_NO_DUPLICATE"
            );
        }
    }

    /**
     * Resolves the "select" query param into a list of valid column names.
     * - null/blank      -> returns null, meaning "all columns"
     * - all invalid      -> throws 400 ValidationException
     * - some valid        -> returns only the valid ones (invalid entries silently dropped)
//     */
//    private List<String> resolveSelectColumns(String select) {
//        if (select == null || select.trim().isEmpty()) {
//            return null; // signal: return full object
//        }
//        List<String> valid = requested.stream()
//                .filter(validColumns.VALID_COLUMNS::contains)
//                .distinct()
//                .collect(Collectors.toList());
//
//        if (valid.isEmpty()) {
//            throw new ValidationException(
//                    "Invalid column name(s) in select: " + select +
//                            "INVALID_SELECT_COLUMN"
//            );
//        }
//
//        return valid; // may be a subset of requested; invalid ones dropped silently
//    }

    /**
     * Builds a full field map, then filters down to resolvedColumns if not null.
     */
    private Map<String, Object> toMap(Vehicle v, Set<String> resolvedColumns) {
        Map<String, Object> full = new LinkedHashMap<>();
        full.put("vehicleId", v.getVehicleId());
        full.put("companyId", v.getCompanyId());
        full.put("vehicleNo", v.getVehicleNo());
        full.put("vehicleType", v.getVehicleType());
        full.put("ownerLedgerId", v.getOwnerLedgerId());
        full.put("createdAt", v.getCreatedAt());
        full.put("updatedAt", v.getUpdatedAt());

        if (resolvedColumns == null || resolvedColumns.isEmpty()) {
            return full;
        }

        Map<String, Object> filtered = new LinkedHashMap<>();
        for (String col : resolvedColumns) {
            filtered.put(col, full.get(col));
        }
        return filtered;
    }
}
