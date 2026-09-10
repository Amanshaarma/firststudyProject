package com.tms.Main.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialInvoiceRequest {

    private LocalDate invoiceDate;

    private String invoiceNo;

    private BigDecimal amount;

}