package com.tms.Main.Service.impl;

import com.tms.Main.Dto.DispatchDetailsRequest;
import com.tms.Main.Mapper.DispatchDetailsMapper;
import com.tms.Main.Mapper.MaterialDetailsMapper;
import com.tms.Main.Model.DispatchDetails;
import com.tms.Main.Model.MaterialDetails;
import com.tms.Main.Model.Trip;
import com.tms.Main.Repository.DispatchDetailsRepository;
import com.tms.Main.Repository.MaterialDetailsRepository;
import com.tms.Main.Service.DispatchDetailsServies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class DispatchDetailsImpl implements DispatchDetailsServies {

        private final DispatchDetailsRepository dispatchRepository;
        private final MaterialDetailsRepository materialRepository;

        private final DispatchDetailsMapper dispatchMapper;
        private final MaterialDetailsMapper materialMapper;

        public void create(
                Trip trip,
                List<DispatchDetailsRequest> requests) {

            if (requests == null || requests.isEmpty()) {
                return;
            }

            for (DispatchDetailsRequest request : requests) {

                DispatchDetails dispatch =
                        dispatchMapper.toEntity(request);

                dispatch.setCompanyId(trip.getCompanyId());
                dispatch.setTripId(trip.getTripId());

                DispatchDetails finalDispatch = dispatchRepository.save(dispatch);

                if (request.getMaterialDetails() == null) {
                    continue;
                }
                List<MaterialDetails> materials =
                        request.getMaterialDetails()
                                .stream()
                                .map(materialMapper::toEntity)
                                .peek(material -> {
                                    material.setCompanyId(
                                            trip.getCompanyId()
                                    );
                                    material.setDispatchDetails(finalDispatch.getDispatchId());
                                })
                                .toList();

                materialRepository.saveAll(materials);
            }
        }
    }
