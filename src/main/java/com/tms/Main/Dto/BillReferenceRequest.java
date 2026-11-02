package com.tms.Main.Dto;


import com.tms.Main.enumData.MethodOfAdj;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillReferenceRequest {

    @NotNull(message = "companyId is required")
    private Long companyId;
    @NotNull(message = "companyId is required")
    private Long ledgerId;

    private String refName;

    private BigDecimal amount;

    private MethodOfAdj methodOfAdj;

    private Long agstBillRefId;

}
