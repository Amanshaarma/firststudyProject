package com.tms.Main.Dto;
import com.tms.Main.enumData.GstRegistrationType;
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

    private String mailingName;

    private String address;

    private String addressLn1;

    private String addressLn2;

    private GstRegistrationType gstRegistrationType;

    private String gstNo;

}
