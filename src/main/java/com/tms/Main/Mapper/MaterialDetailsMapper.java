package com.tms.Main.Mapper;

import com.tms.Main.Dto.MaterialDetailsRequest;
import com.tms.Main.Model.MaterialDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MaterialDetailsMapper {

    MaterialDetailsRequest toDto(MaterialDetails entity);

    MaterialDetails toEntity(MaterialDetailsRequest dto);

    void updateEntityFromDto(
            MaterialDetailsRequest dto,
            @MappingTarget MaterialDetails entity
    );
}
