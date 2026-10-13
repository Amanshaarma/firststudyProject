package com.tms.Main.Service.impl;

import com.tms.Main.Expection.ResourceNotFoundException;
import com.tms.Main.Model.IndianCities;
import com.tms.Main.Model.Trip;
import com.tms.Main.Model.TripDestination;
import com.tms.Main.Repository.IndianCitiesRepository;
import com.tms.Main.Repository.TripDestinationRepository;
import com.tms.Main.Repository.TripsRepository;
import com.tms.Main.Service.TripDestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

    @Service
    @RequiredArgsConstructor
    @Transactional
    public class TripDestinationServiceImpl implements TripDestinationService {

        private final TripDestinationRepository tripDestinationRepository;
        private final TripsRepository tripRepository;
        private final IndianCitiesRepository indianCityRepository;


    @Override
    public List<TripDestination> addDestination(Long tripId, List<Long> cityIds) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trip not found with id: " + tripId
                        )
                );

        List<Long> uniqueCityIds = cityIds.stream()
                .distinct()
                .toList();

        List<IndianCities> cities = indianCityRepository.findAllById(uniqueCityIds);

        if (cities.size() != uniqueCityIds.size()) {
            throw new ResourceNotFoundException(
                    "One or more cities were not found"
            );
        }

        Map<Long, IndianCities> cityMap = cities.stream()
                .collect(Collectors.toMap(
                        IndianCities::getId,
                        Function.identity()
                ));

        List<TripDestination> destinations = IntStream
                .range(0, uniqueCityIds.size())
                .mapToObj(index -> {

                    Long cityId = uniqueCityIds.get(index);

                    if (tripDestinationRepository
                            .existsByTripTripIdAndCityId(tripId, cityId)) {

                        throw new IllegalArgumentException(
                                "City " + cityId +
                                        " is already added to this trip"
                        );
                    }

                    TripDestination destination = new TripDestination();

                    destination.setTrip(trip);
                    destination.setCity(cityMap.get(cityId));
                    destination.setSequenceNo(index + 1);

                    return destination;
                })
                .toList();

        return tripDestinationRepository.saveAll(destinations);
    }

    @Override
        @Transactional(readOnly = true)
        public List<TripDestination> getDestinationsByTripId(Long tripId) {

            return tripDestinationRepository
                    .findByTripTripIdOrderBySequenceNoAsc(tripId);
        }

        @Override
        @Transactional(readOnly = true)
        public TripDestination getDestinationById(Long tripDestinationId) {

            return tripDestinationRepository.findById(tripDestinationId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Trip destination not found with id: "
                                            + tripDestinationId
                            )
                    );
        }

        @Override
        public TripDestination updateDestination(
                Long tripDestinationId,
                Long cityId,
                Integer sequenceNo
        ) {

            TripDestination destination = getDestinationById(tripDestinationId);

            if (!destination.getCity().getId().equals(cityId)
                    && tripDestinationRepository
                    .existsByTripTripIdAndCityId(
                            destination.getTrip().getTripId(),
                            cityId
                    )) {

                throw new IllegalArgumentException(
                        "City is already added to this trip"
                );
            }


            IndianCities city = indianCityRepository.findById(cityId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "City not found with id: " + cityId
                            )
                    );

            destination.setCity(city);
            destination.setSequenceNo(sequenceNo);

            return tripDestinationRepository.save(destination);
        }

        @Override
        public void deleteDestination(Long tripDestinationId) {

            TripDestination destination = getDestinationById(tripDestinationId);

            tripDestinationRepository.delete(destination);
        }

        @Override
        public void deleteDestinationsByTripId(Long tripId) {

            tripDestinationRepository.deleteByTripTripId(tripId);
        }
    }

