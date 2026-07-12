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

    @NotNull(message = "companyId is required")
    private Long companyId;

    @NotBlank(message = "vehicleNo cannot be blank")
    @Size(max = 50, message = "vehicleNo cannot exceed 50 characters")
    private String vehicleNo;

    @Size(max = 100, message = "vehicleType cannot exceed 100 characters")
    private String vehicleType;

    // Optional - if provided, must exist and belong to the same companyId
    private Long ownerLedgerId;
}
