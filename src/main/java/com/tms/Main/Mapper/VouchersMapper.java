package com.tms.Main.Mapper;

import com.tms.Main.Dto.VoucherRequest;
import com.tms.Main.Model.Vouchers;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VouchersMapper {

    VoucherRequest toDto(Vouchers entity);

    Vouchers toEntity(VoucherRequest dto);

    void updateEntityFromDto(
            VoucherRequest dto,
            @MappingTarget Vouchers entity
    );
}
