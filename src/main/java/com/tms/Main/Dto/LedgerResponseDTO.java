package com.tms.Main.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tms.Main.enumData.GstRegType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LedgerResponseDTO {
	private Long ledgerId;
	private Long companyId;
	private Long groupId;
	private String ledgerName;
	private BigDecimal openingBalance;
	private Boolean isBillByBill;
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
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String country;
    private String pinCode;
    private String groupName;
    private GstRegType gstRegType;
}
