package com.tms.Main.Mapper;

import com.tms.Main.Dto.EWayDetailsRequest;
import com.tms.Main.Model.EwayDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EwayDetailsMapper {

    EWayDetailsRequest toDto(EwayDetails entity);

    EwayDetails toEntity(EWayDetailsRequest dto);

    void updateEntityFromDto(
            EWayDetailsRequest dto,
            @MappingTarget EwayDetails entity
    );
}
