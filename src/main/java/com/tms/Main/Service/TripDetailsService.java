package com.tms.Main.Service;

import com.tms.Main.Dto.TripRequest;
import com.tms.Main.Model.Trip;

public interface TripDetailsService {
    public Trip createTrip(
            TripRequest request,
            Long salesVoucherId,
            Long purchaseVoucherId);

    public void createTripDetails(
            Trip trip,
            TripRequest request);
}
