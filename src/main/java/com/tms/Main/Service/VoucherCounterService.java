package com.tms.Main.Service;

public interface VoucherCounterService {
    public Long nextVoucherNumber(
            Long companyId,
            String financialYear);
}
