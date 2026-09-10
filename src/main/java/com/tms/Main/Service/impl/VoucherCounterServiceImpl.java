package com.tms.Main.Service.impl;

import com.tms.Main.Repository.VoucherCounterRepository;
import com.tms.Main.Service.VoucherCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherCounterServiceImpl  implements VoucherCounterService {

    private final VoucherCounterRepository repository;

    public Long nextVoucherNumber(
            Long companyId,
            String financialYear) {

        return repository.getNextVoucherNumber(
                companyId,
                financialYear
        );
    }
}