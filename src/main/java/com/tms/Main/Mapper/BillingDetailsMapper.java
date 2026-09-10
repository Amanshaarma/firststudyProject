package com.tms.Main.Mapper;

import com.tms.Main.Dto.BillingDetailsRequest;
import com.tms.Main.Model.BillingDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BillingDetailsMapper {

    BillingDetailsRequest toDto(BillingDetails entity);

    BillingDetails toEntity(BillingDetailsRequest dto);

    void updateEntityFromDto(
            BillingDetailsRequest dto,
            @MappingTarget BillingDetails entity
    );
}
