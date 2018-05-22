package com.oen.core.exception;

public class OENPermissionException extends RuntimeException {
	
	private static final long serialVersionUID = 8640656852777031507L;
	
	private String message;
	
	
	public String getMessage() {
		return message;
	}
	public OENPermissionException(String message) {
		super(message);
		this.message = message;
	}
	
	public OENPermissionException() {
		super();
	}

}
