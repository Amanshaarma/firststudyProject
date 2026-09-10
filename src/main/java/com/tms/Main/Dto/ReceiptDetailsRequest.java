package com.tms.Main.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetailsRequest {

    private LocalDate supplierInvDate;

    private String supplierInvNo;

    private String receiptDocNo;

}
