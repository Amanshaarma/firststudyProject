package com.tms.Main.Mapper;

import com.tms.Main.Dto.BillReferenceRequest;
import com.tms.Main.Dto.BillingDetailsRequest;
import com.tms.Main.Model.BillReference;
import com.tms.Main.Model.BillingDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BillReferenceMapper {

    BillReferenceRequest toDto(BillReference entity);

    BillReference toEntity(BillReferenceRequest dto);

    void updateEntityFromDto(
            BillReferenceRequest dto,
            @MappingTarget BillReference entity
    );
}
