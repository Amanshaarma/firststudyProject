package com.tms.Main.Service.impl;

import com.tms.Main.Dto.BillReferenceRequest;
import com.tms.Main.Model.BillReference;
import com.tms.Main.Repository.BillReferenceRepository;
import com.tms.Main.Service.BillRefrenceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BillRefrenceImpl implements BillRefrenceService {

    private  final BillReferenceRepository billReferenceRepository;
    @Override
    public void createBillReference(List<BillReferenceRequest> billReferenceRequest, Long voucherId) {

        List<BillReference> billReferences = billReferenceRequest
                .stream()
                .map(billRef -> BillReference.builder()
                        .voucherId(voucherId)
                        .refName(billRef.getRefName())
                        .ledgerId(billRef.getLedgerId())
                        .amount(billRef.getAmount())
                        .companyId(billRef.getCompanyId())
                        .mthdOfAdj(billRef.getMethodOfAdj())
                        .agstBillReference(billRef.getAgstBillRefId())
                        .voucherId(voucherId)
                        .build()).toList();
        billReferenceRepository.saveAll(billReferences);
    }
}
