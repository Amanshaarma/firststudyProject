package com.tms.Main.Service.impl;

import com.tms.Main.Dto.ReceiptDetailsRequest;
import com.tms.Main.Mapper.ReceiptDetailsMapper;
import com.tms.Main.Model.ReceiptDetails;
import com.tms.Main.Model.Trip;
import com.tms.Main.Repository.ReceiptDetailsRepository;
import com.tms.Main.Service.ReceiptDetailsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptDetailsImpl implements ReceiptDetailsService {

    private final ReceiptDetailsRepository repository;
    private final ReceiptDetailsMapper mapper;

    public void create(
            Trip trip,
            ReceiptDetailsRequest request) {

        if (request == null) {
            return;
        }

        ReceiptDetails receipt =
                mapper.toEntity(request);

        receipt.setCompanyId(trip.getCompanyId());
        receipt.setTripId(trip.getTripId());

        repository.save(receipt);
    }
}
