package com.tms.Main.Service;

import com.tms.Main.Dto.TripResponse;
import com.tms.Main.Dto.TripTransactionRequest;
import com.tms.Main.response.ApiResponsePattern;
import jakarta.validation.Valid;

public interface TripCreationService {
    ApiResponsePattern<Object> createTrip(@Valid TripTransactionRequest request);
}
