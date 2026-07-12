package com.study.Main.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

//entity/GlobalGroup.java
@Entity
@Table(name = "global_groups", indexes = { @Index(name = "idx_global_group_name", columnList = "group_name") })
public class GlobalGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "global_group_id")
	private Long globalGroupId;

	@Column(name = "group_name", nullable = false, unique = true)
	private String groupName;

	public Long getGlobalGroupId() {
		return globalGroupId;
	}

	public void setGlobalGroupId(Long globalGroupId) {
		this.globalGroupId = globalGroupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}

