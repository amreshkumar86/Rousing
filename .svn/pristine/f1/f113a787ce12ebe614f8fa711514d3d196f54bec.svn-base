package com.oen.api.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oen.core.config.UserResponseMessage;
import com.oen.core.domain.CustItemDashboardObject;
import com.oen.core.domain.DeviceData;
import com.oen.core.domain.SuccessResponse;
import com.oen.core.domain.model.ItemAttributeSchedule;
import com.oen.core.service.DeviceHandlerService;

@RestController
@RequestMapping("/device")
public class DeviceHandlerController {
	
	
	@Value("${token.header}")
    private String tokenHeader;
	
	@Autowired
	private DeviceHandlerService deviceHandlerService;
	
	private SuccessResponse successResponse = null;
	
	
	@RequestMapping(value = "/add/new", 
			method = RequestMethod.POST ,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNewDevice(HttpServletRequest request, 
		@RequestBody DeviceData deviceObj) {
		String token = request.getHeader(tokenHeader);
		deviceHandlerService.addNewDevice(token, deviceObj);
		successResponse = new SuccessResponse(HttpStatus.OK.value(), UserResponseMessage.DEVICE_ADDED_MESSAGE);
		return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/item/list/{customerId}", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItemList(HttpServletRequest request,
			@PathVariable("customerId")String customerId) throws JsonProcessingException {
		String itemList = deviceHandlerService.getAllCustomerItems(customerId);
		return new ResponseEntity<String>(itemList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/item/list_with_complete_data/{customerId}", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getItemListCompleteData(HttpServletRequest request,
			@PathVariable("customerId")String customerId) throws JsonProcessingException {
		//String itemList = deviceHandlerService.getAllCustomerItemsAndData(customerId);
		List<CustItemDashboardObject> dashboardList = deviceHandlerService.getOenDeviceDashbordData(customerId);
		return new ResponseEntity<List<CustItemDashboardObject>>(dashboardList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/save/attribute/state", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveDeviceAttributeState(HttpServletRequest request,
			@RequestBody DeviceData deviceObj) throws JsonProcessingException {
		String token = request.getHeader(tokenHeader);
		deviceHandlerService.saveDeviceAttributeState(token, deviceObj);
		successResponse = new SuccessResponse(HttpStatus.OK.value(), UserResponseMessage.ITEM_STATE_SAVE_MESSAGE);
		return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/schedule", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> scheduleAnDevice(HttpServletRequest request,
			@RequestBody ItemAttributeSchedule obj) throws JsonProcessingException {
		deviceHandlerService.scheduleAnCustItem(obj);
		successResponse = new SuccessResponse(HttpStatus.OK.value(), UserResponseMessage.ITEM_SCHEDULE_SAVE_MESSAGE);
		return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/change/nickname", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changeNickName(HttpServletRequest request,
			@RequestBody JSONObject obj) throws JsonProcessingException {
		deviceHandlerService.changeDeviceNickName(obj);
		successResponse = new SuccessResponse(HttpStatus.OK.value(), UserResponseMessage.ITEM_NICKNAME_SAVE_MESSAGE);
		return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update/sleep/timer", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateSleepTimer(HttpServletRequest request,
			@RequestBody JSONObject obj) throws JsonProcessingException {
		String token = request.getHeader(tokenHeader);
		deviceHandlerService.updateSleepTimer(token, obj);
		successResponse = new SuccessResponse(HttpStatus.OK.value(), 
				UserResponseMessage.SLEEP_TIMER_UPDATE_MESSAGE);
		return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/sleep/timer/{customerItemId}", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getLastSleeperVal(HttpServletRequest request,
			@PathVariable("customerItemId") Long custItemId) 
			throws JsonProcessingException {
		//String token = request.getHeader(tokenHeader);
		String response = deviceHandlerService.getLastSleeperVal(custItemId);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{customerItemId}", 
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteDevice(HttpServletRequest request,
			@PathVariable("customerItemId") Long custItemId) 
			throws JsonProcessingException {
		String token = request.getHeader(tokenHeader);
		deviceHandlerService.deleteDevice(token, custItemId);
		successResponse = new SuccessResponse(HttpStatus.OK.value(), 
				UserResponseMessage.DEVICE_DELETED_MESSAGE);
		return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
	}

}
