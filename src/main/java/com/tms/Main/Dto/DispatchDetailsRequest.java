package com.tms.Main.Dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class DispatchDetailsRequest {

    private LocalDate lrDate; // Handled as LocalDate for proper date manipulation
    private String lrNo;
    private double netWt;
    private double grossWt;
    private String dispatchFrom;
    private String dispatchTo;
    private String consignorName;
    private String consignorAddress;
    private String consignorGstn;
    private String consigneeName;
    private String consigneeAddress;
    private String consigneeGstn;
    private List<MaterialDetailsRequest> materialDetails;

}
