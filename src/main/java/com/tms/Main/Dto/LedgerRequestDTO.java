package com.tms.Main.Dto;

import java.math.BigDecimal;

import com.tms.Main.enumData.GstRegType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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