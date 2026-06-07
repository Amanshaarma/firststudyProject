package com.study.Main.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDTO {

	@NotNull(message = "User ID is required")
	private Long userId;

	@NotBlank(message = "Company name is required")
	private String companyName;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String emailId;

	@NotBlank(message = "Mobile number is required")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
	private String mobileNo;

	@NotBlank(message = "PAN number is required")
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
	private String panNumber;

	@Pattern(regexp = "[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}", message = "Invalid GST format")
	private String gstNo;

	@NotBlank(message = "Address is required")
	private String address;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CompanyRequestDTO [userId=" + userId + ", companyName=" + companyName + ", emailId=" + emailId
				+ ", mobileNo=" + mobileNo + ", panNumber=" + panNumber + ", gstNo=" + gstNo + ", address=" + address
				+ "]";
	}
	
	
}