package com.tms.Main.Service.impl;

import com.tms.Main.Dto.EWayDetailsRequest;
import com.tms.Main.Mapper.EwayDetailsMapper;
import com.tms.Main.Model.EwayDetails;
import com.tms.Main.Model.Trip;
import com.tms.Main.Repository.EwayDetailsRepository;
import com.tms.Main.Service.EwayDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EwayDetailsImpl implements EwayDetailsService {
    private final EwayDetailsRepository repository;
    private final EwayDetailsMapper mapper;

    public void create(
            Trip trip,
            EWayDetailsRequest request) {

        if (request == null) {
            return;
        }

        EwayDetails eway =
                mapper.toEntity(request);

        eway.setCompanyId(trip.getCompanyId());
        eway.setTripId(trip.getTripId());

        repository.save(eway);
    }
}
