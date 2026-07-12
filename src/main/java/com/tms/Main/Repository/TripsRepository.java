package com.tms.Main.Repository;

import com.tms.Main.Model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripsRepository extends JpaRepository<Trip,Long> {
    long countByVehicleVehicleId(Long vehicleId);
}
