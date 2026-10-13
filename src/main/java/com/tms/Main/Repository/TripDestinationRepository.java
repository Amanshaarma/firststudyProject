package com.tms.Main.Repository;

import com.tms.Main.Model.TripDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface TripDestinationRepository extends JpaRepository<TripDestination, Long> {

        List<TripDestination> findByTripTripIdOrderBySequenceNoAsc(Long tripId);

        boolean existsByTripTripIdAndCityId(Long tripId, Long cityId);

        void deleteByTripTripId(Long tripId);
    }
