package com.tms.Main.Service.impl;

import com.tms.Main.Repository.VoucherCounterRepository;
import com.tms.Main.Service.VoucherCounterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherCounterServiceImpl  implements VoucherCounterService {

    private final VoucherCounterRepository repository;

    @Transactional
    public Long nextVoucherNumber(
            Long companyId,
            String financialYear,
            Long voucherTypeId) {

        return repository.getNextVoucherNumber(
                companyId,
                voucherTypeId,
                financialYear
        );
    }
}