package com.tms.Main.Mapper;

import com.tms.Main.Dto.DispatchDetailsRequest;
import com.tms.Main.Model.DispatchDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DispatchDetailsMapper {

    DispatchDetailsRequest toDto(DispatchDetails entity);

    DispatchDetails toEntity(DispatchDetailsRequest dto);

    void updateEntityFromDto(
            DispatchDetailsRequest dto,
            @MappingTarget DispatchDetails entity
    );
}
