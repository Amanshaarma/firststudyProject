package com.tms.Main.Repository;

import com.tms.Main.Model.EwayDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EwayDetailsRepository
        extends JpaRepository<EwayDetails, Long> {

    List<EwayDetails> findByCompanyId(Long companyId);

    List<EwayDetails> findByTripId(Long tripId);
}
