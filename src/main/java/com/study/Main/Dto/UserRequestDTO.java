package com.study.Main.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

	@NotBlank(message = "Username is required")
	private String userName;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password; // raw password — hash in service

	@NotBlank(message = "Plan type is required")
	private String planType;

	@NotNull(message = "Joining date is required")
	private LocalDate joiningDate;

	private LocalDate subscriptionStart;
	private LocalDate subscriptionEnd;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public LocalDate getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}
	public LocalDate getSubscriptionStart() {
		return subscriptionStart;
	}
	public void setSubscriptionStart(LocalDate subscriptionStart) {
		this.subscriptionStart = subscriptionStart;
	}
	public LocalDate getSubscriptionEnd() {
		return subscriptionEnd;
	}
	public void setSubscriptionEnd(LocalDate subscriptionEnd) {
		this.subscriptionEnd = subscriptionEnd;
	}
	@Override
	public String toString() {
		return "UserRequestDTO [userName=" + userName + ", password=" + password + ", planType=" + planType
				+ ", joiningDate=" + joiningDate + ", subscriptionStart=" + subscriptionStart + ", subscriptionEnd="
				+ subscriptionEnd + "]";
	}
	
	
}
