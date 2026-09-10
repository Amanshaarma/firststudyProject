package com.tms.Main.Repository;

import com.tms.Main.Model.VoucherEntries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherEntriesRepository
        extends JpaRepository<VoucherEntries, Long> {

    List<VoucherEntries> findByVoucherId(Long voucherId);

    List<VoucherEntries> findByLedgerId(Long ledgerId);

    List<VoucherEntries> findByCompanyId(Long companyId);
}
