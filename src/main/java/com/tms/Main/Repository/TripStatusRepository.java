package com.tms.Main.Repository;

import com.tms.Main.Model.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripStatusRepository
        extends JpaRepository<TripStatus, Long> {

    List<TripStatus> findByTripId(Long tripId);

    List<TripStatus> findByCompanyId(Long companyId);
}
