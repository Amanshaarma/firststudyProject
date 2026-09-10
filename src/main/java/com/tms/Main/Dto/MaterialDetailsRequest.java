package com.tms.Main.Dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDetailsRequest {

    private String packageName;

    private String description;

    private BigDecimal netWeight;

    private BigDecimal grossWeight;

    private BigDecimal amount;

}
