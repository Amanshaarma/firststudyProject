package com.tms.Main.Mapper;

import com.tms.Main.Dto.TripRequest;
import com.tms.Main.Dto.TripResponse;
import com.tms.Main.Dto.TripTransactionRequest;
import com.tms.Main.Model.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TripMapper {

    TripResponse toDto(Trip entity);

    Trip toEntity(TripRequest dto);

    void updateEntityFromDto(
            TripTransactionRequest dto,
            @MappingTarget Trip entity
    );
}