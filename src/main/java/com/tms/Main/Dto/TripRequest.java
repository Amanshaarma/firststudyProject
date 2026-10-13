package com.tms.Main.Dto;


import com.tms.Main.enumData.FreightType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripRequest {

    private Long companyId;

    private LocalDate tripDate;

    private Long vehicleId;

    private Long partyLedgerId;

    private Long supplierLedgerId;

    private Long salesLedgerId;

    private Long purchaseLedgerId;

    private BigDecimal partyWeight;

    private BigDecimal supplierWeight;

    private BigDecimal partyRate;

    private BigDecimal supplierRate;

    private BigDecimal partyFreight;

    private BigDecimal supplierFreight;

    private FreightType partyFreightType;

    private FreightType supplierFreightType;

    private Long fromCityId;

    private List<Long> toCityId;

    private String narration;

    private List<Long> lrId;

    private List<DispatchDetailsRequest> dispatchDetails;

    private List<MaterialInvoiceRequest> materialInvoice;

    private BillingDetailsRequest partyBilling;

    private BillingDetailsRequest supplierBilling;

    private ReceiptDetailsRequest receiptDetails;

    private EWayDetailsRequest eWayDetails;

    private TripStatusRequest ulPoint;
    private TripStatusRequest tripCompleted;
    private TripStatusRequest podRcv;
    private TripStatusRequest podSubmit;

}