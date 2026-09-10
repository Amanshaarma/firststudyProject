package com.tms.Main.Service;

import com.tms.Main.Dto.EWayDetailsRequest;
import com.tms.Main.Model.Trip;

public interface EwayDetailsService {
    public void create(Trip trip, EWayDetailsRequest eWayDetails);
}
