package com.study.Main.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ledgers", indexes = { @Index(name = "idx_ledger_company_id", columnList = "company_id"),
		@Index(name = "idx_ledger_group_id", columnList = "group_id"),
		@Index(name = "idx_ledger_name", columnList = "ledger_name") })
@NoArgsConstructor
@AllArgsConstructor
public class Ledger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ledger_id")
	private Long ledgerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private CompanyProfiles companyProfile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;

	@Column(name = "ledger_name", nullable = false)
	private String ledgerName;

	@Column(name = "opening_balance", nullable = false)
	private BigDecimal openingBalance = BigDecimal.ZERO;

	@Column(name = "is_bill_by_bill", nullable = false)
	private Boolean isBillByBill = false;

	@Column(name = "mailing_name")
	private String mailingName;

	@Column(name = "address")
	private String address;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "bank_ac_no")
	private String bankAcNo;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "bank_ac_holder_name")
	private String bankAcHolderName;

	@Column(name = "branch_name")
	private String branchName;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public Long getLedgerId() {
		return ledgerId;
	}

	public void setLedgerId(Long ledgerId) {
		this.ledgerId = ledgerId;
	}

	public CompanyProfiles getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(CompanyProfiles companyProfile) {
		this.companyProfile = companyProfile;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
