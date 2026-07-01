package com.study.Main.Dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LedgerRequestDTO {

	@NotNull(message = "Company ID is required")
	private Long companyId;

	@NotNull(message = "Group ID is required")
	private Long groupId;

	@NotBlank(message = "Ledger name is required")
	private String ledgerName;

	private BigDecimal openingBalance = BigDecimal.ZERO;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public Boolean getIsBillByBill() {
		return isBillByBill;
	}

	public void setIsBillByBill(Boolean isBillByBill) {
		this.isBillByBill = isBillByBill;
	}

	public String getMailingName() {
		return mailingName;
	}

	public void setMailingName(String mailingName) {
		this.mailingName = mailingName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getBankAcNo() {
		return bankAcNo;
	}

	public void setBankAcNo(String bankAcNo) {
		this.bankAcNo = bankAcNo;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAcHolderName() {
		return bankAcHolderName;
	}

	public void setBankAcHolderName(String bankAcHolderName) {
		this.bankAcHolderName = bankAcHolderName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}