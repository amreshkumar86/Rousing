package com.oen.core.domain;

public class SuccessResponse {
	
	
	public Integer code;
	public String message;
	public Long entityId;
	
	public SuccessResponse() {
	}
	
	public SuccessResponse(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public SuccessResponse(Integer code, String message, Long entityId) {
		this.code = code;
		this.message = message;
		this.entityId = entityId;
	}
	
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public Long getEntityId() {
		return entityId;
	}

}
