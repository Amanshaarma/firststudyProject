package com.tms.Main.Mapper;

import com.tms.Main.Dto.ReceiptDetailsRequest;
import com.tms.Main.Model.ReceiptDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReceiptDetailsMapper {

    ReceiptDetailsRequest toDto(ReceiptDetails entity);

    ReceiptDetails toEntity(ReceiptDetailsRequest dto);

    void updateEntityFromDto(
            ReceiptDetailsRequest dto,
            @MappingTarget ReceiptDetails entity
    );
}
