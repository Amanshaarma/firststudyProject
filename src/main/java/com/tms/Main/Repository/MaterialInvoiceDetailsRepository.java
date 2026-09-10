package com.tms.Main.Repository;

import com.tms.Main.Model.MaterialInvDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialInvoiceDetailsRepository extends JpaRepository<MaterialInvDetails, Long> {

    List<MaterialInvDetails> findByCompanyId(Long companyId);

    boolean existsByCompanyIdAndInvoiceNo(
            Long companyId,
            Long invoiceNo
    );
}
