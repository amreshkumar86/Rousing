package com.oen.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;

@Entity
@Table(name = "app_version")
@JsonFilter("APP_VERSION_FILTER")
public class AppVersion extends BaseModel {
	
	private static final long serialVersionUID = 4103398089239849431L;
	
	private Long versionCode;
	private String versionNumber;
	private String updateType;
	
	@Column(name = "version_code")
	public Long getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(Long versionCode) {
		this.versionCode = versionCode;
	}
	
	@Column(name = "version_number")
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	
	@Column(name = "update_type")
	public String getUpdateType() {
		return updateType;
	}
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
}
