package com.oen.core.service;

import java.text.ParseException;
import java.util.List;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oen.core.domain.EnergyDashboardInfo;

public interface EnergyService {
	
	/**
	 * @return energy management dashboard details.
	 */
	List<EnergyDashboardInfo> frameEnergyDashboardObj(JSONObject requestObject);
	
	
	/**
	 * @param custId :: Customer Id
	 *        custItemId :: Customer Item Id
	 * 
	 * @return :: EnergyDashboardInfo
	 */
	EnergyDashboardInfo getDeatilsByDeviceType(Long custId);  
	
	
	String getEnergyGraphData(String token, JSONObject requestObject)throws JsonProcessingException,ParseException;

}
