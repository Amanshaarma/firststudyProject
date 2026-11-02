package com.tms.Main.Service.impl;

import com.tms.Main.Dto.BillingDetailsRequest;
import com.tms.Main.Expection.ResourceNotFoundException;
import com.tms.Main.Mapper.BillingDetailsMapper;
import com.tms.Main.Model.BillingDetails;
import com.tms.Main.Model.CompanyProfiles;
import com.tms.Main.Model.Ledger;
import com.tms.Main.Repository.BillingDetailsRepository;
import com.tms.Main.Repository.LedgerRepository;
import com.tms.Main.Service.BillingDetailsService;
import com.tms.Main.Service.CompanyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BillingDetailsImpl implements BillingDetailsService {

    private BillingDetailsRepository billingDetailsRepository;
    private BillingDetailsMapper billingDetailsMapper;
    private final LedgerRepository ledgerRepository;
    private final CompanyService companyService;


    @Transactional
    private Long create( BillingDetailsRequest requests,Long companyId) {
        if (requests == null)
        {
            return 0L;
        }
        BillingDetails bill = billingDetailsMapper.toEntity(requests);
        Ledger ledger = ledgerRepository.findById(requests.getLedgerId()).orElseThrow(()-> new ResourceNotFoundException("LEdger id is not present "));
        bill.setLedger(ledger);
        CompanyProfiles companyProfiles = companyService.getCompanyById(companyId);
        bill.setCompanyId(companyProfiles);
        BillingDetails newBill  = billingDetailsRepository.save(bill);
        return newBill.getBillId();
    }
    @Transactional
    @Override
    public Long createSupplierBilling(BillingDetailsRequest requests,Long companyId) {

        return create(requests,companyId);
    }
    @Transactional
    @Override
    public Long createPartyBilling(BillingDetailsRequest requests,Long companyId) {
        return create(requests,companyId);
    }
}
