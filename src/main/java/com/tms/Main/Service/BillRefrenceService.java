package com.tms.Main.Service;

import com.tms.Main.Dto.BillReferenceRequest;

import java.util.List;

public interface BillRefrenceService {

    public void createBillReference(List<BillReferenceRequest> billReferenceRequest, Long voucherId);
}
