package com.tms.Main.Service;

import com.tms.Main.Dto.BillingDetailsRequest;

public interface BillingDetailsService {
    public Long createSupplierBilling(
            BillingDetailsRequest requests,Long companyId);
    public Long createPartyBilling(BillingDetailsRequest requests,Long companyId);
}
