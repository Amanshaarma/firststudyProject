package com.tms.Main.Service.impl;

import com.tms.Main.Dto.MaterialInvoiceRequest;
import com.tms.Main.Mapper.MaterialInvoiceRequestMapper;
import com.tms.Main.Mapper.MaterialInvoiceRequestMapperImpl;
import com.tms.Main.Model.MaterialInvDetails;
import com.tms.Main.Model.Trip;
import com.tms.Main.Repository.MaterialInvoiceDetailsRepository;
import com.tms.Main.Service.MaterialDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialDetailsServiceImpl implements MaterialDetailsService {

    private final MaterialInvoiceDetailsRepository repository;
    private final MaterialInvoiceRequestMapper mapper;

    public void create(
            Trip trip,
            List<MaterialInvoiceRequest> requests) {

        if (requests == null || requests.isEmpty()) {
            return;
        }

        List<MaterialInvDetails> invoices =
                requests.stream()
                        .map(mapper::toEntity)
                        .peek(invoice ->
                                invoice.setCompanyId(
                                        trip.getCompanyId()
                                ))
                        .toList();

        repository.saveAll(invoices);
    }
}