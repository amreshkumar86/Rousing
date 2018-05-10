package com.oen.core.domain;

import java.io.Serializable;

public class EnergyDashboardInfo implements Serializable {

	private static final long serialVersionUID = 5261905936321313369L;
	
	private String deviceType;
	private Integer totalCount;
	private Integer activeCount;
	private Integer activeBelowMidValue;
	private Double totalKWH;
	
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getActiveCount() {
		return activeCount;
	}
	public void setActiveCount(Integer activeCount) {
		this.activeCount = activeCount;
	}
	public Integer getActiveBelowMidValue() {
		return activeBelowMidValue;
	}
	public void setActiveBelowMidValue(Integer activeBelowMidValue) {
		this.activeBelowMidValue = activeBelowMidValue;
	}
	public Double getTotalKWH() {
		return totalKWH;
	}
	public void setTotalKWH(Double totalKWH) {
		this.totalKWH = totalKWH;
	}
	
	
	
}
