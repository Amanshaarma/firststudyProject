package com.tms.Main.Service;

import com.tms.Main.Dto.DispatchDetailsRequest;
import com.tms.Main.Model.Trip;

import java.util.List;

public interface DispatchDetailsServies
{
    public void create(
            Trip trip,
            List<DispatchDetailsRequest> requests);

}
