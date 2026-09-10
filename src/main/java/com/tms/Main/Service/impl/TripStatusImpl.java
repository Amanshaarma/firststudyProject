package com.tms.Main.Service.impl;

import com.tms.Main.Dto.TripStatusRequest;
import com.tms.Main.Mapper.TripStatusMapper;
import com.tms.Main.Model.Trip;
import com.tms.Main.Model.TripStatus;
import com.tms.Main.Repository.TripStatusRepository;
import com.tms.Main.Service.TripStatusService;
import com.tms.Main.enumData.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TripStatusImpl implements TripStatusService {

    private final TripStatusRepository repository;
    private final TripStatusMapper mapper;

    public void create(
            Trip trip,
            TripStatusRequest ulPoint,
            TripStatusRequest tripCompleted,
            TripStatusRequest podRcv,
            TripStatusRequest podSubmit) {

        List<TripStatus> statuses = new ArrayList<>(4);

        addStatus(
                statuses,
                trip,
                ulPoint,
                StatusType.UL_POINT
        );

        addStatus(
                statuses,
                trip,
                tripCompleted,
                StatusType.TRIP_COMPLETED
        );

        addStatus(
                statuses,
                trip,
                podRcv,
                StatusType.POD_RECEIVED
        );

        addStatus(
                statuses,
                trip,
                podSubmit,
                StatusType.POD_SUBMITTED
        );

        if (!statuses.isEmpty()) {
            repository.saveAll(statuses);
        }
    }

    private void addStatus(
            List<TripStatus> statuses,
            Trip trip,
            TripStatusRequest request,
            StatusType statusType) {

        if (request == null) {
            return;
        }

        TripStatus status =
                mapper.toEntity(request);

        status.setCompanyId(trip.getCompanyId());
        status.setTripId(trip.getTripId());
        status.setStatusType(statusType);

        statuses.add(status);
    }
}