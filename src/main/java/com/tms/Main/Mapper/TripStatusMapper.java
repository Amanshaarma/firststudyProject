package com.tms.Main.Mapper;

import com.tms.Main.Dto.TripStatusRequest;
import com.tms.Main.Model.TripStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TripStatusMapper {

    TripStatusRequest toDto(TripStatus entity);

    TripStatus toEntity(TripStatusRequest dto);

    void updateEntityFromDto(
            TripStatusRequest dto,
            @MappingTarget TripStatus entity
    );
}
