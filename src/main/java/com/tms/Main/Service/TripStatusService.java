package com.tms.Main.Service;

import com.tms.Main.Dto.TripStatusRequest;
import com.tms.Main.Model.Trip;

public interface TripStatusService {
    public void create(
            Trip trip,
            TripStatusRequest ulPoint,
            TripStatusRequest tripCompleted,
            TripStatusRequest podRcv,
            TripStatusRequest podSubmit);
}
