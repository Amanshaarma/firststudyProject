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
    INSERT INTO voucher_sequences
        (
            company_id,
            voucher_type_id,
            fiscal_year,
            last_number,
            updated_at
        )
    VALUES
        (
            :companyId,
            :voucherTypeId,
            :fiscalYear,
            1,
            CURRENT_TIMESTAMP
        )
    ON CONFLICT (
        company_id,
        voucher_type_id,
        fiscal_year
    )
    DO UPDATE SET
        last_number = voucher_sequences.last_number + 1,
        updated_at = CURRENT_TIMESTAMP
    RETURNING last_number
    """, nativeQuery = true)
    Long getNextVoucherNumber(
            @Param("companyId") Long companyId,
            @Param("voucherTypeId") Long voucherTypeId,
            @Param("fiscalYear") String fiscalYear
    );
}
