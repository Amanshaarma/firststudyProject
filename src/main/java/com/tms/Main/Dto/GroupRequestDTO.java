package com.tms.Main.Dto;

import com.tms.Main.enumData.GroupType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GroupRequestDTO {


	private Long companyId;

	private Long globalGroupId; // optional

	private Long parentGroupId; // optional — null if root group


	private String groupName;


	private GroupType groupType;

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

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	@Override
	public String toString() {
		return "GroupRequestDTO [companyId=" + companyId + ", globalGroupId=" + globalGroupId + ", parentGroupId="
				+ parentGroupId + ", groupName=" + groupName + ", groupType=" + groupType + "]";
	}
}