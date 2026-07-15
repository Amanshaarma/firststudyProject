package com.tms.Main.Repository;

import com.tms.Main.Model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// =============================================================================
// VEHICLE REPOSITORY
// =============================================================================
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, VehicleRepositoryCustom {

    // POST/PUT duplicate check - case-insensitive, scoped to company
    @Query("SELECT v FROM Vehicle v WHERE v.companyId = :companyId AND LOWER(v.vehicleNo) = LOWER(:vehicleNo)")
    Optional<Vehicle> findByCompanyIdAndVehicleNoIgnoreCase(
            @Param("companyId") Long companyId,
            @Param("vehicleNo") String vehicleNo
    );

    @Query(value = "SELECT v FROM Vehicle v LEFT JOIN FETCH v.ownerLedger WHERE v.companyId = :companyId",
            countQuery = "SELECT COUNT(v) FROM Vehicle v WHERE v.companyId = :companyId")
    Page<Vehicle> findByCompanyId(@Param("companyId") Long companyId, Pageable pageable);

    @Query(value = "SELECT v FROM Vehicle v LEFT JOIN FETCH v.ownerLedger " +
            "WHERE v.companyId = :companyId AND v.ownerLedger.ledgerId = :ownerLedgerId",
            countQuery = "SELECT COUNT(v) FROM Vehicle v " +
                    "WHERE v.companyId = :companyId AND v.ownerLedger.ledgerId = :ownerLedgerId")
    Page<Vehicle> findByCompanyIdAndOwnerLedger(@Param("companyId") Long companyId,
                                                @Param("ownerLedgerId") Long ownerLedgerId,
                                                Pageable pageable);

    @Query("SELECT v FROM Vehicle v LEFT JOIN FETCH v.ownerLedger WHERE v.vehicleId = :vehicleId")
    Optional<Vehicle> findByIdWithOwnerLedger(@Param("vehicleId") Long vehicleId);
}