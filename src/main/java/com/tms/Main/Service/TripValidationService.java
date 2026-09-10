package com.tms.Main.Service;

import com.tms.Main.Dto.TripTransactionRequest;

public interface TripValidationService {
    void validate(TripTransactionRequest request);
}
