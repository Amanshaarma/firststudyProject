package com.tms.Main.Service;

import com.tms.Main.Model.TripDestination;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TripDestinationService {
    List<TripDestination> addDestination(
            Long tripId,
            List<Long> cityId
    );

    List<TripDestination> getDestinationsByTripId(Long tripId);

    TripDestination getDestinationById(Long tripDestinationId);

    TripDestination updateDestination(
            Long tripDestinationId,
            Long cityId,
            Integer sequenceNo
    );

    void deleteDestination(Long tripDestinationId);

    void deleteDestinationsByTripId(Long tripId);
}
