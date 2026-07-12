package com.tms.Main.Dto;

import jakarta.validation.constraints.NotBlank;


public class GlobalGroupRequestDTO {

 @NotBlank(message = "Group name is required")
 private String groupName;

 public String getGroupName() {
	return groupName;
 }

 public void setGroupName(String groupName) {
	this.groupName = groupName;
 }
 
}