package com.study.Main.Model;

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
@Table(name = "groups", indexes = { @Index(name = "idx_group_name", columnList = "group_name"),
		@Index(name = "idx_group_type", columnList = "group_type"),
		@Index(name = "idx_company_id", columnList = "company_id") })

@NoArgsConstructor
@AllArgsConstructor
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private Long groupId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "global_group_id")
	private GlobalGroup globalGroup;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private CompanyProfiles companyProfile;

	// Self referencing — a group can have a parent group
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_group_id")
	private Group parentGroup;

	@Column(name = "group_name", nullable = false)
	private String groupName;

	@Column(name = "group_type", nullable = false)
	private String groupType;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public GlobalGroup getGlobalGroup() {
		return globalGroup;
	}

	public void setGlobalGroup(GlobalGroup globalGroup) {
		this.globalGroup = globalGroup;
	}

	public CompanyProfiles getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(CompanyProfiles companyProfile) {
		this.companyProfile = companyProfile;
	}

	public Group getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(Group parentGroup) {
		this.parentGroup = parentGroup;
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

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", globalGroup=" + globalGroup + ", companyProfile=" + companyProfile
				+ ", parentGroup=" + parentGroup + ", groupName=" + groupName + ", groupType=" + groupType
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}