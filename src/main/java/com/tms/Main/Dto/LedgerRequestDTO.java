package com.tms.Main.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tms.Main.enumData.GstRegType;
import com.tms.Main.enumData.LedgerType;
import com.tms.Main.enumData.SupplyType;
import com.tms.Main.enumData.TaxType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LedgerRequestDTO {

	@NotNull(message = "Company ID is required")
	private Long companyId;

	@NotNull(message = "Group ID is required")
	private Long groupId;

	@NotBlank(message = "Ledger name is required")
	private String ledgerName;

    private LedgerType typeOfLedger;
    private TaxType taxType;

    @NotNull(message = "isGstApplicable is required")
    @JsonProperty("isGstApplicable")
    private Boolean isGstApplicable;

    @Pattern(regexp = "^[A-Za-z0-9]{1,8}$", message = "hsnSac must be alphanumeric, max 8 characters")
    private String hsnSac;

    @DecimalMin(value = "0.0", message = "gstRate must be 0 or more")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal gstRate;

    private SupplyType typeOfSupply;

	private BigDecimal openingBalance = BigDecimal.ZERO;
    private GstRegType gstRegType;
	private Boolean isBillByBill = false;
	private String mailingName;
	private String address;
	private String mobileNo;
	private String emailId;
	private String gstNo;
	private String panNo;
	private String bankAcNo;
	private String ifscCode;
	private String bankName;
	private String bankAcHolderName;
	private String branchName;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String country;
    private String pinCode;


}