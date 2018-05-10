package com.oen.core.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.oen.core.domain.BaseModel;

@Entity
@Table(name = "manufacturer")
@JsonFilter("MANUFACTURE_FILTER")
public class Manufacture extends BaseModel {

	private static final long serialVersionUID = -9162444633666947603L;
	
	private String name;
	private String serverUrl;
	private String alternateServerUrl;
	private String authUserName;
	private String authPassword;
	
	
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "server_url")
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	@Column(name = "alternate_server_url")
	public String getAlternateServerUrl() {
		return alternateServerUrl;
	}
	public void setAlternateServerUrl(String alternateServerUrl) {
		this.alternateServerUrl = alternateServerUrl;
	}
	
	@Column(name = "auth_uname")
	public String getAuthUserName() {
		return authUserName;
	}
	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
	}
	
	@Column(name = "auth_password")
	public String getAuthPassword() {
		return authPassword;
	}
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}
	
	

}
