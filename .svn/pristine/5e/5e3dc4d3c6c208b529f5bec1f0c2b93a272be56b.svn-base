package com.oen.api.web;

import java.text.ParseException;
import java.util.List;

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
import com.oen.core.domain.EnergyDashboardInfo;
import com.oen.core.service.EnergyService;

@RestController
@RequestMapping("/energy")
public class EnergyManagementController {
	
	@Value("${token.header}")
    private String tokenHeader;
	
	@Autowired
	private EnergyService energyService;
	
	@RequestMapping(value = "/get/dashboard", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDashboard(HttpServletRequest request,
			@RequestBody JSONObject requestObject) throws JsonProcessingException {
		List<EnergyDashboardInfo> dashboardInfo = energyService.frameEnergyDashboardObj(requestObject);
		return new ResponseEntity<List<EnergyDashboardInfo>>(dashboardInfo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get/energy/graph", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEnergyGraph(HttpServletRequest request,
			@RequestBody JSONObject requestObject) throws JsonProcessingException,ParseException {
		String token = request.getHeader(tokenHeader);
		String graphData = energyService.getEnergyGraphData(token, requestObject);
		return new ResponseEntity<String>(graphData, HttpStatus.OK);
	}
	
	

}
