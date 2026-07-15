package com.tms.Main.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request body for POST /api/vehicles and PUT /api/vehicles/{vehicleId}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {

    private Long companyId;

    private String vehicleNo;

    private String vehicleType;

    // Optional - if provided, must exist and belong to the same companyId
    private Long ownerLedgerId;
}
