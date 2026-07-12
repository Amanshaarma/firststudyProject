package com.tms.Main.Repository;


import com.tms.Main.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// =============================================================================
// VEHICLE REPOSITORY
// =============================================================================
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // GET (All) - companyId only
    List<Vehicle> findByCompanyId(Long companyId);

    // GET (All) - companyId + ownerLedgerId
    List<Vehicle> findByCompanyIdAndOwnerLedgerId(Long companyId, Long ownerLedgerId);

    // POST/PUT duplicate check - case-insensitive, scoped to company
    @Query("SELECT v FROM Vehicle v WHERE v.companyId = :companyId AND LOWER(v.vehicleNo) = LOWER(:vehicleNo)")
    Optional<Vehicle> findByCompanyIdAndVehicleNoIgnoreCase(
            @Param("companyId") Long companyId,
            @Param("vehicleNo") String vehicleNo
    );
}


