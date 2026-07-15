package com.tms.Main.Service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.Main.Dto.VehicleRequest;
import com.tms.Main.Dto.VehicleResponseDTO;
import com.tms.Main.Expection.ConstraintViolationException;
import com.tms.Main.Expection.DuplicateResourceException;
import com.tms.Main.Mapper.VehicleMapper;
import com.tms.Main.Model.Vehicle;
import com.tms.Main.Model.Ledger;
import com.tms.Main.Repository.CompanyRepository;
import com.tms.Main.Repository.LedgerRepository;
import com.tms.Main.Repository.TripsRepository;
import com.tms.Main.Repository.VehicleRepository;
import com.tms.Main.util.ValidColumns;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tms.Main.Expection.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    private final VehicleMapper vehicleMapper;
    private final ObjectMapper objectMapper;

    private VehicleRepository vehicleRepository;


    private CompanyRepository companyRepository;


    private LedgerRepository ledgerRepository;

    private TripsRepository tripRepository;

    private static final int MAX_PAGE_SIZE = 200;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, TripsRepository tripRepository, ValidColumns validColumns, VehicleMapper vehicleMapper, ObjectMapper objectMapper, CompanyRepository companyRepository, LedgerRepository ledgerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.objectMapper = objectMapper;
        this.companyRepository = companyRepository;
        this.ledgerRepository = ledgerRepository;
        this.validColumns = validColumns;
        this.tripRepository = tripRepository;
    }

    // =========================================================================
    // GET (All) - single consolidated endpoint backs this
    //   companyId          -> required
    //   ownerLedgerId       -> optional
    //   select              -> optional
    // =========================================================================


    public Page<Map<String, Object>> getVehicles(Long companyId, Long ownerLedgerId, List<String> select,
                                                 int page, int size) {
        validateCompanyExists(companyId);

        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("page must be >= 0 and size must be > 0");
        }
        if (size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Page size must not exceed " + MAX_PAGE_SIZE);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("vehicleNo").ascending());

        Page<Vehicle> vehiclePage;
        if (ownerLedgerId != null) {
            validateLedgerBelongsToCompany(ownerLedgerId, companyId);
            vehiclePage = vehicleRepository.findByCompanyIdAndOwnerLedger(companyId, ownerLedgerId, pageable);
        } else {
            vehiclePage = vehicleRepository.findByCompanyId(companyId, pageable);
        }

        Set<String> resolvedColumns = validColumns.resolveFields(select, "VEHICLE_COLUMNS");
        return vehiclePage.map(v -> toMap(vehicleMapper.toDTO(v), resolvedColumns));
    }

    public Map<String, Object> getVehicleById(Long vehicleId, List<String> select) {
        Vehicle vehicle = vehicleRepository.findByIdWithOwnerLedger(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + vehicleId));

        Set<String> resolvedColumns = validColumns.resolveFields(select, "VEHICLE_COLUMNS");
        return toMap(vehicleMapper.toDTO(vehicle), resolvedColumns);
    }

    private Map<String, Object> toMap(VehicleResponseDTO dto, Set<String> resolvedColumns) {
        Map<String, Object> full = objectMapper.convertValue(dto, new TypeReference<Map<String, Object>>() {});

        if (resolvedColumns == null || resolvedColumns.isEmpty()) {
            full.remove("ownerLedgerName");
            return full;
        }

        Map<String, Object> filtered = new LinkedHashMap<>();
        for (String col : resolvedColumns) {
            if (full.containsKey(col)) {
                filtered.put(col, full.get(col));
            }
        }
        if(!resolvedColumns.contains("ownerLedgerName"))
            filtered.remove("ownerLedgerName");
        return filtered;
    }

    // =========================================================================
    // POST - Create
    // =========================================================================
    @Transactional
    public Map<String, Object> createVehicle(VehicleRequest request) {
        log.info("Creating vehicle: companyId={}, vehicleNo={}", request.getCompanyId(), request.getVehicleNo());

        validateCompanyExists(request.getCompanyId());
        checkVehicleNoDuplicate(request.getCompanyId(), request.getVehicleNo());

        Ledger ownerLedger = null;
        if (request.getOwnerLedgerId() != null && request.getOwnerLedgerId() > 0) {
            validateLedgerBelongsToCompany(request.getOwnerLedgerId(), request.getCompanyId());
            ownerLedger = ledgerRepository.findById(request.getOwnerLedgerId())
                    .orElseThrow(() -> new ResourceNotFoundException("ledger id is not found" + request.getOwnerLedgerId()) );

        }
        Vehicle vehicle = new Vehicle();
        vehicle.setCompanyId(request.getCompanyId());
        vehicle.setVehicleNo(request.getVehicleNo().trim());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setOwnerLedger(ownerLedger);

        Vehicle saved = vehicleRepository.save(vehicle);
        log.info("Vehicle created: vehicleId={}", saved.getVehicleId());

        return toMap(vehicleMapper.toDTO(saved), null); // full object on create
    }

    // =========================================================================
    // UPDATE - same conditions as POST
    // =========================================================================
    @Transactional
    public Map<String, Object> updateVehicle(Long vehicleId, VehicleRequest request) {
        log.info("Updating vehicle: vehicleId={} ", vehicleId);

        Vehicle vehicle = findVehicleOrThrow(vehicleId);
        if (request.getCompanyId() != null )
            validateCompanyExists(request.getCompanyId());

        if (request.getCompanyId() != null && !vehicle.getCompanyId().equals(request.getCompanyId())) {
            throw new ValidationException("Cannot change companyId for an existing vehicle" + " COMPANY_ID_IMMUTABLE");
        }

        // Only re-check duplicate if vehicleNo actually changed (case-insensitive compare)
        if ( request.getVehicleNo() != null && !request.getVehicleNo().isEmpty() && !vehicle.getVehicleNo().equalsIgnoreCase(request.getVehicleNo().trim())) {
            checkVehicleNoDuplicate(request.getCompanyId(), request.getVehicleNo());
        }
        Ledger ownerLedger =null;
        if (request.getOwnerLedgerId() != null ) {
            validateLedgerBelongsToCompany(request.getOwnerLedgerId(), request.getCompanyId());
            ownerLedger = ledgerRepository.findById(request.getOwnerLedgerId())
                    .orElseThrow(() -> new ResourceNotFoundException("ledger id is not found" + request.getOwnerLedgerId()) );

        }

        vehicle.setVehicleNo(request.getVehicleNo() != null && !request.getVehicleNo().isEmpty() ? request.getVehicleNo().trim() : vehicle.getVehicleNo());
        vehicle.setVehicleType(request.getVehicleType() != null  && !request.getVehicleType().isEmpty() ? request.getVehicleType().trim() : vehicle.getVehicleType());
        vehicle.setOwnerLedger(ownerLedger);

        Vehicle updated = vehicleRepository.save(vehicle);
        log.info("Vehicle updated: vehicleId={} ", vehicleId);

        return toMap(vehicleMapper.toDTO(updated), null);
    }

    // =========================================================================
    // DELETE - only if no trips reference this vehicle
    // =========================================================================
    @Transactional
    public void deleteVehicle(Long vehicleId) {
        log.info("Deleting vehicle: vehicleId={}", vehicleId);

        Vehicle vehicle = findVehicleOrThrow(vehicleId);

        long tripCount = tripRepository.countByVehicleVehicleId(vehicleId);
        if (tripCount > 0) {
            throw new ConstraintViolationException("Cannot delete vehicle: " + tripCount + " trip(s) reference this vehicle" + "VEHICLE_HAS_DEPENDENT_TRIPS" + "trips", "409");
        }

        vehicleRepository.delete(vehicle);
        log.info("Vehicle deleted: vehicleId={}", vehicleId);
    }

    // =========================================================================
    // HELPERS
    // =========================================================================

    private Vehicle findVehicleOrThrow(Long vehicleId) {
        return vehicleRepository.findById(vehicleId).orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + vehicleId + " VEHICLE_NOT_FOUND"));
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
        Ledger ledger = ledgerRepository.findById(ledgerId).orElseThrow(() -> new ResourceNotFoundException("Ledger not found with ID: " + ledgerId + " LEDGER_NOT_FOUND"));

        if (!ledger.getCompanyProfile().getCompanyId().equals(companyId)) {
            throw new ValidationException("ownerLedgerId " + ledgerId + " does not belong to companyId " + companyId + " LEDGER_COMPANY_MISMATCH");
        }
    }

    private void checkVehicleNoDuplicate(Long companyId, String vehicleNo) {
        if (vehicleNo == null || vehicleNo.trim().isEmpty()) {
            throw new ValidationException("vehicleNo cannot be null or empty" + " VEHICLE_NO_REQUIRED");
        }
        boolean exists = vehicleRepository.findByCompanyIdAndVehicleNoIgnoreCase(companyId, vehicleNo.trim()).isPresent();
        if (exists) {
            throw new DuplicateResourceException("Vehicle with number '" + vehicleNo + "' already exists for this company" + " VEHICLE_NO_DUPLICATE");
        }
    }



    /**
     * Builds a full field map, then filters down to resolvedColumns if not null.
     */
//    private Map<String, Object> toMap(Vehicle v, Set<String> resolvedColumns) {
//        Map<String, Object> full = new LinkedHashMap<>();
//        full.put("vehicleId", v.getVehicleId());
//        full.put("companyId", v.getCompanyId());
//        full.put("vehicleNo", v.getVehicleNo());
//        full.put("vehicleType", v.getVehicleType());
//        if (v.getOwnerLedger() != null && v.getOwnerLedger().getLedgerId() > 0)
//        {
//            full.put("ownerLedgerId", v.getOwnerLedger().getLedgerId());
//        }else {
//            full.put("ownerLedgerId", null);
//        }
//        full.put("createdAt", v.getCreatedAt());
//        full.put("updatedAt", v.getUpdatedAt());
//
//        if (resolvedColumns == null || resolvedColumns.isEmpty()) {
//            return full;
//        }
//
//        Map<String, Object> filtered = new LinkedHashMap<>();
//        for (String col : resolvedColumns) {
//            filtered.put(col, full.get(col));
//        }
//        return filtered;
//    }
}
