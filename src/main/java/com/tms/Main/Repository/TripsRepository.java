package com.tms.Main.Repository;

import com.tms.Main.Model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface TripsRepository extends JpaRepository<Trip,Long> {
    long countByVehicleId(Long vehicleId);
}
