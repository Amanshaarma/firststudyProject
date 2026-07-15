package com.tms.Main.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleResponseDTO {

    private Long vehicleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long companyId;

    private String vehicleNo;

    private String vehicleType;

    // Optional - if provided, must exist and belong to the same companyId
    private Long ownerLedgerId;

    private String ownerLedgerName;

}
