package com.tms.Main.Mapper;

import com.tms.Main.Dto.MaterialInvoiceRequest;
import com.tms.Main.Model.MaterialInvDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface MaterialInvoiceRequestMapper {

        MaterialInvoiceRequest toDto(MaterialInvDetails entity);

        MaterialInvDetails toEntity(MaterialInvoiceRequest dto);

        void updateEntityFromDto(
                MaterialInvoiceRequestMapper dto,
                @MappingTarget MaterialInvDetails entity
        );
}
