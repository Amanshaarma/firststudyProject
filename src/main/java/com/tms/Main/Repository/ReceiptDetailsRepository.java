package com.tms.Main.Repository;

import com.tms.Main.Model.ReceiptDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptDetailsRepository
        extends JpaRepository<ReceiptDetails, Long> {

    List<ReceiptDetails> findByCompanyId(Long companyId);

    List<ReceiptDetails> findByTripId(Long tripId);
}
