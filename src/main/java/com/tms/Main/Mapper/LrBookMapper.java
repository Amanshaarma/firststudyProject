package com.tms.Main.Mapper;

import com.tms.Main.Dto.LRBookRequest;
import com.tms.Main.Model.LrBook;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LrBookMapper {

    LRBookRequest toDto(LrBook entity);

    LrBook toEntity(LRBookRequest dto);

    void updateEntityFromDto(
            LRBookRequest dto,
            @MappingTarget LrBook entity
    );
}