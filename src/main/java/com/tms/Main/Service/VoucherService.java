package com.tms.Main.Service;

import com.tms.Main.Dto.VoucherRequest;

public interface VoucherService {
    public Long createSalesVoucher(VoucherRequest request);
    public Long createPurchaseVoucher(VoucherRequest request);
}
