package com.tms.Main.Repository;

import com.tms.Main.Dto.VoucherCounterId;
import com.tms.Main.Model.VoucherCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherCounterRepository
        extends JpaRepository<VoucherCounter, VoucherCounterId> {
    @Query(value = """
    INSERT INTO voucher_counters
        (company_id, financial_year, last_voucher_no, updated_at)
    VALUES
        (:companyId, :financialYear, 1, CURRENT_TIMESTAMP)
    ON CONFLICT (company_id, financial_year)
    DO UPDATE SET
        last_voucher_no = voucher_counters.last_voucher_no + 1,
        updated_at = CURRENT_TIMESTAMP
    RETURNING last_voucher_no
    """, nativeQuery = true)
    Long getNextVoucherNumber(
            @Param("companyId") Long companyId,
            @Param("financialYear") String financialYear
    );
}
