package com.tms.Main.Service.impl;


import com.tms.Main.Dto.TripResponse;
import com.tms.Main.Dto.TripTransactionRequest;
import com.tms.Main.Model.Trip;
import com.tms.Main.Repository.TripsRepository;
import com.tms.Main.Service.*;
import com.tms.Main.response.ApiResponsePattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripCreationService {

    private final VoucherService voucherService;
    private final TripDetailsService tripDetailsService;
    private final TripValidationService validationService;
    private final LrBookService lrBookService;

    @Transactional
    public ApiResponsePattern<Object> createTrip(TripTransactionRequest request) {

        validationService.validate(request);

        /*
         * 1. Create Sales Voucher
         */
        Long salesVoucherId =
                voucherService.createSalesVoucher(
                        request.getSalesVoucher()
                );

        /*
         * 2. Create Purchase Voucher
         */
        Long purchaseVoucherId =
                voucherService.createPurchaseVoucher(
                        request.getPurchaseVoucher()
                );

        /*
         * 3. Create Trip
         */
        Trip trip = tripDetailsService.createTrip(
                request.getTrip(),
                salesVoucherId,
                purchaseVoucherId
        );
        boolean check = lrBookService.updateLrBookTripId(trip,request.getTrip().getLrId());
        /*
         * 4. Create all trip-related details
         */
        tripDetailsService.createTripDetails(
                trip,
                request.getTrip()
        );

        return ApiResponsePattern.success(null,"trip created sucessfully");
    }
}
