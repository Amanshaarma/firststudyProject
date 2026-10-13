package com.tms.Main.Service.impl;

import com.tms.Main.Dto.TripRequest;
import com.tms.Main.Mapper.TripMapper;
import com.tms.Main.Model.Trip;
import com.tms.Main.Repository.TripsRepository;
import com.tms.Main.Service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TripDetailsServiceImpl implements TripDetailsService {

        private final TripsRepository tripRepository;
        private final TripMapper tripMapper;
        private final DispatchDetailsServies dispatchDetailsService;
        private final MaterialDetailsServiceImpl invoiceService;
        private final ReceiptDetailsService receiptDetailsService;
        private final EwayDetailsService ewayDetailsService;
        private final TripStatusService tripStatusService;
        private  final  TripDestinationService tripDestinationService;


        public Trip createTrip(
                TripRequest request,
                Long salesVoucherId,
                Long purchaseVoucherId) {

            Trip trip = tripMapper.toEntity(request);
            trip.setSaleVoucherId(salesVoucherId);
            trip.setPurchaseVoucherId(purchaseVoucherId);

            return tripRepository.save(trip);
        }

        public void createTripDetails(
                Trip trip,
                TripRequest request) {

            dispatchDetailsService.create(
                    trip,
                    request.getDispatchDetails()
            );

            tripDestinationService.addDestination(trip.getTripId(),request.getToCityId());

            invoiceService.create(
                    trip,
                    request.getMaterialInvoice()
            );

            receiptDetailsService.create(
                    trip,
                    request.getReceiptDetails()
            );

            ewayDetailsService.create(
                    trip,
                    request.getEWayDetails()
            );

            tripStatusService.create(
                    trip,
                    request.getUlPoint(),
                    request.getTripCompleted(),
                    request.getPodRcv(),
                    request.getPodSubmit()
            );
        }
    }
