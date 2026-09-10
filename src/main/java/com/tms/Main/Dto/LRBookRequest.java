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
public class LRBookRequest {

    private LocalDate lrDate;

    private String lrNo;

    private Long consignorId;

    private String consignorName;

    private String consignorAddress;

    private String consignorAddressLn1;

    private String consignorAddressLn2;

    private String consignorGstNo;

    private Long consigneeId;

    private String consigneeName;

    private String consigneeAddress;

    private String consigneeAddressLn1;

    private String consigneeAddressLn2;

    private String consigneeGstNo;

}