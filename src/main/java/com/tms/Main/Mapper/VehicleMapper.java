package com.tms.Main.Mapper;

import com.tms.Main.Dto.VehicleResponseDTO;
import com.tms.Main.Model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "ownerLedgerId", source = "ownerLedger.ledgerId")
    @Mapping(target = "ownerLedgerName", source = "ownerLedger.ledgerName")
    VehicleResponseDTO toDTO(Vehicle vehicle);

    List<VehicleResponseDTO> toDTOList(List<Vehicle> vehicles);
}
