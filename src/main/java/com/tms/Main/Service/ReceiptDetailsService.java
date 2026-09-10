package com.tms.Main.Service;

import com.tms.Main.Dto.ReceiptDetailsRequest;
import com.tms.Main.Model.Trip;

public interface ReceiptDetailsService {
    public void create(Trip trip, ReceiptDetailsRequest receiptDetails);
}
