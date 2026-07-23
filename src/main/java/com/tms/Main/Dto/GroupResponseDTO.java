package com.tms.Main.Dto;

import java.time.LocalDateTime;

public class GroupResponseDTO {

	private Long groupId;
	private Long globalGroupId;
	private Long companyId; // only id — not full object
	private Long parentGroupId; // only id — not full object
	private String groupName;
	private String groupType;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
    private  Boolean isDefault;

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getGlobalGroupId() {
		return globalGroupId;
	}

	public void setGlobalGroupId(Long globalGroupId) {
		this.globalGroupId = globalGroupId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	@Override
	public String toString() {
		return "GroupResponseDTO [groupId=" + groupId + ", globalGroupId=" + globalGroupId + ", companyId=" + companyId
				+ ", parentGroupId=" + parentGroupId + ", groupName=" + groupName + ", groupType=" + groupType
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}