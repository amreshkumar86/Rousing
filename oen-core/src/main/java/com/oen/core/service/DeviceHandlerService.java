package com.oen.core.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oen.core.domain.CustItemDashboardObject;
import com.oen.core.domain.DeviceData;
import com.oen.core.domain.model.AttributeMaster;
import com.oen.core.domain.model.CustomerItems;
import com.oen.core.domain.model.ItemAttributeSchedule;
import com.oen.core.domain.model.ItemAttributeState;

public interface DeviceHandlerService {
	
	void addNewDevice(String token, DeviceData deviceObj);
	
	String getAllCustomerItems(String customerId) throws JsonProcessingException;
	
	String getAllCustomerItemsAndData(String customerId) throws JsonProcessingException;
	
	List<CustItemDashboardObject> getOenDeviceDashbordData(String customerId);
	
	String saveDeviceAttributeState(String token, DeviceData deviceObj);
	
	ItemAttributeState saveCurrentState(CustomerItems custItem, 
			AttributeMaster attribute, String attributeValue, Double consumedPower);
	
	void scheduleAnCustItem(ItemAttributeSchedule itemAttrSchedule);
	
	void changeDeviceNickName(JSONObject obj);
	
	void updateSleepTimer(String token, JSONObject requestObject);

	String getLastSleeperVal(Long custItemId) throws JsonProcessingException;
	
	void deleteDevice(String token, Long custItemId);
}
