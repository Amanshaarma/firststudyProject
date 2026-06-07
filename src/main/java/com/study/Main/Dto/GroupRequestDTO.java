package com.study.Main.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GroupRequestDTO {

	@NotNull(message = "Company ID is required")
	private Long companyId;

	private Long globalGroupId; // optional

	private Long parentGroupId; // optional — null if root group

	@NotBlank(message = "Group name is required")
	private String groupName;

	@NotBlank(message = "Group type is required")
	private String groupType;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getGlobalGroupId() {
		return globalGroupId;
	}

	public void setGlobalGroupId(Long globalGroupId) {
		this.globalGroupId = globalGroupId;
	}

	public Long getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(Long parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	@Override
	public String toString() {
		return "GroupRequestDTO [companyId=" + companyId + ", globalGroupId=" + globalGroupId + ", parentGroupId="
				+ parentGroupId + ", groupName=" + groupName + ", groupType=" + groupType + "]";
	}

}
