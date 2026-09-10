package com.tms.Main.Mapper;

import com.tms.Main.Dto.VoucherEntryRequest;
import com.tms.Main.Model.VoucherEntries;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoucherEntriesMapper {

    VoucherEntryRequest toDto(VoucherEntries entity);

    VoucherEntries toEntity(VoucherEntryRequest dto);

    void updateEntityFromDto(
            VoucherEntryRequest dto,
            @MappingTarget VoucherEntries entity
    );
}
