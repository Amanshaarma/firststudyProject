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
public class EWayDetailsRequest {

    private LocalDate billDate;

    private String billNo;

    private LocalDate expiryDate;

}
