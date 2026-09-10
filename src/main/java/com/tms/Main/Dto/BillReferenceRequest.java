package com.tms.Main.Dto;



import com.tms.Main.enumData.MethodOfAdj;
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

    private Long companyId;

    private Long ledgerId;

    private String refName;

    private BigDecimal amount;

    private MethodOfAdj methodOfAdj;

    private Long agstBillRefId;

}
