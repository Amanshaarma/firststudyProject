package com.tms.Main.Repository;

import com.tms.Main.Model.Vouchers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VouchersRepository
        extends JpaRepository<Vouchers, Long> {

    List<Vouchers> findByCompanyId(Long companyId);

    List<Vouchers> findByCompanyIdAndFiscalYear(
            Long companyId,
            String fiscalYear
    );

    Optional<Vouchers> findByCompanyIdAndFiscalYearAndVoucherNo(
            Long companyId,
            String fiscalYear,
            Long voucherNo
    );

    boolean existsByCompanyIdAndFiscalYearAndVoucherNo(
            Long companyId,
            String fiscalYear,
            Long voucherNo
    );
}