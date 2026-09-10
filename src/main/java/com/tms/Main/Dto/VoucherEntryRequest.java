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
public class VoucherEntryRequest {

    private Long ledgerId;

    private BigDecimal amount;

    private String description;

}
