package com.tms.Main.Dto;


import com.tms.Main.enumData.SourceType;
import com.tms.Main.enumData.VoucherMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherRequest {

    @NotNull
    private Long companyId;

    @NotNull
    private Long voucherTypeId;

    @NotNull
    private LocalDate voucherDate;

    private String narration;

    private SourceType sourceType;

    private String fiscalYear;

    private VoucherMode voucherMode;

    @Valid
    @NotNull
    private List<VoucherEntryRequest> voucherEntries;

}