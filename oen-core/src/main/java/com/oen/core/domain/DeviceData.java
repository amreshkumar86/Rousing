package com.oen.core.domain;

import java.io.Serializable;

public class DeviceData implements Serializable {
	
	private static final long serialVersionUID = 8917885268306230561L;
	
	private Long customerId;
	private Long userId;
	private Long itemId;
	private Long customerItemId;
	private Long itemAttributeId;
	private Long manufactureId;
	private String itemUniqueNumber;
	private String itemBatchNumber;
	private String deviceNickName;
	private String attributeValue;
	
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getCustomerItemId() {
		return customerItemId;
	}
	public void setCustomerItemId(Long customerItemId) {
		this.customerItemId = customerItemId;
	}
	public Long getItemAttributeId() {
		return itemAttributeId;
	}
	public void setItemAttributeId(Long itemAttributeId) {
		this.itemAttributeId = itemAttributeId;
	}
	public Long getManufactureId() {
		return manufactureId;
	}
	public void setManufactureId(Long manufactureId) {
		this.manufactureId = manufactureId;
	}
	public String getItemUniqueNumber() {
		return itemUniqueNumber;
	}
	public void setItemUniqueNumber(String itemUniqueNumber) {
		this.itemUniqueNumber = itemUniqueNumber;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public String getDeviceNickName() {
		return deviceNickName;
	}
	public void setDeviceNickName(String deviceNickName) {
		this.deviceNickName = deviceNickName;
	}
	public String getItemBatchNumber() {
		return itemBatchNumber;
	}
	public void setItemBatchNumber(String itemBatchNumber) {
		this.itemBatchNumber = itemBatchNumber;
	}
}
