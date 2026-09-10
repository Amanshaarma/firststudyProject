package com.tms.Main.Repository;

import com.tms.Main.Model.DispatchDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DispatchDetailsRepository
        extends JpaRepository<DispatchDetails, Long> {

    List<DispatchDetails> findByCompanyId(Long companyId);

    List<DispatchDetails> findByTripId(Long tripId);
}