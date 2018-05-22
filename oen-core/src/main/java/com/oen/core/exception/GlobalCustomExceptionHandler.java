package com.oen.core.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;


@RestControllerAdvice
public class GlobalCustomExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRuntimeExceptionHandler.class);
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(OENPermissionException.class)
    public ExceptionResponse handleOenPermissionException(MultipartException ex, HttpServletRequest request) {
		LOGGER.error("OENPermissionException Occured :: URL = "+request.getRequestURL());
		LOGGER.error("Exception :: "+ex);
		ex.printStackTrace();
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse();
		exceptionResponse.setCode(ExceptionConstantCodes.PERMISSION_DENIED_CODE);
		exceptionResponse.setMessage(ex.getMessage());
		return exceptionResponse;

	}
}
