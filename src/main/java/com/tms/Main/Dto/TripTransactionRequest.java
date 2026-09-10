package com.tms.Main.Dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripTransactionRequest {

    @Valid
    @NotNull
    private VoucherRequest salesVoucher;

    @Valid
    @NotNull
    private VoucherRequest purchaseVoucher;

    @Valid
    @NotNull
    private TripRequest trip;
}