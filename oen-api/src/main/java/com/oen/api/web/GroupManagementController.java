package com.oen.api.web;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oen.core.config.UserResponseMessage;
import com.oen.core.domain.GroupDeviceData;
import com.oen.core.domain.SuccessResponse;
import com.oen.core.service.GroupManagementService;

@RestController
@RequestMapping("/group")
public class GroupManagementController {
	
	private SuccessResponse response =null;
	
	@Value("${token.header}")
    private String tokenHeader;
	
	@Autowired
	private GroupManagementService groupManagementService;
	
	
	
	@RequestMapping(value = "/create/new", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createNewGroup(HttpServletRequest request,
			@RequestBody GroupDeviceData groupInfo) throws JsonProcessingException {
		String token = request.getHeader(tokenHeader);
		groupManagementService.createNewGroup(token, groupInfo);
		response = new SuccessResponse(HttpStatus.OK.value(), 
				UserResponseMessage.GROUP_CREATED_MESSAGE);
		return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update/name", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateGroupName(HttpServletRequest request,
			@RequestBody JSONObject requestObject) throws JsonProcessingException,ParseException {
		String token = request.getHeader(tokenHeader);
		groupManagementService.updateGroupName(token, requestObject);
		response = new SuccessResponse(HttpStatus.OK.value(), 
				UserResponseMessage.GROUP_NAME_CHANGED_MESSAGE);
		return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/save/attribute/state", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveGroupDeviceAttributeState(HttpServletRequest request,
			@RequestBody GroupDeviceData groupInfo) throws JsonProcessingException {
		String token = request.getHeader(tokenHeader);
		groupManagementService.saveGroupDeviceAttributeState(token, groupInfo);
		response = new SuccessResponse(HttpStatus.OK.value(), 
				UserResponseMessage.GROUP_ITEM_STATE_SAVE_MESSAGE);
		return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update/devices/mapping", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateGroupDeviceMapping(HttpServletRequest request,
			@RequestBody GroupDeviceData groupInfo) throws JsonProcessingException {
		String token = request.getHeader(tokenHeader);
		groupManagementService.updateGroupDeviceMapping(token, groupInfo);
		response = new SuccessResponse(HttpStatus.OK.value(), 
				UserResponseMessage.GROUP_DEVICE_UPDATE_MESSAGE);
		return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(HttpServletRequest request,
			@RequestBody GroupDeviceData groupInfo) throws JsonProcessingException {
		String token = request.getHeader(tokenHeader);
		groupManagementService.deleteGroup(token, groupInfo);
		response = new SuccessResponse(HttpStatus.OK.value(), 
				UserResponseMessage.GROUP_DELETED_MESSAGE);
		return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
	}
	
	
}
