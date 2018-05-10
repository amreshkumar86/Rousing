package com.oen.core.domain;

import java.io.Serializable;
import java.util.List;

public class GroupDeviceData implements Serializable  {

	private static final long serialVersionUID = 1000866915229853803L;
	
	private Long groupId;
	private String groupName;
	private List<Long> customerItemIds;
	private List<DeviceAttributeInfo> deviceInfo;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<Long> getCustomerItemIds() {
		return customerItemIds;
	}
	public void setCustomerItemIds(List<Long> customerItemIds) {
		this.customerItemIds = customerItemIds;
	}
	public List<DeviceAttributeInfo> getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(List<DeviceAttributeInfo> deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
	
}
