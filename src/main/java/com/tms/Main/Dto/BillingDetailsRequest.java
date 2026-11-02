package com.tms.Main.Dto;
import com.tms.Main.enumData.GstRegistrationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingDetailsRequest {

    private String partyName;

    @NotNull(message = "Ledger is required")
    private Long ledgerId;

    private String mailingName;

    private String address;

    private String addressLn1;

    private String addressLn2;

    private GstRegistrationType gstRegistrationType;

    private String gstNo;

}
