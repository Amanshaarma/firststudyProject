package com.tms.Main.Repository;

import com.tms.Main.Model.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingDetailsRepository
        extends JpaRepository<BillingDetails, Long> {
}
