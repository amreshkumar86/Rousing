package com.oen.api.web;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oen.core.service.AppVersionService;

@RestController
@RequestMapping("/version")
public class AppVersionController {
	
	@Autowired
	private AppVersionService appVersionService;
	

	@RequestMapping(method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getLatestVersionData(HttpServletRequest request) 
			throws JsonProcessingException, ParseException {
		String versiondata  = appVersionService.getLatestAppVersionData() ;
		return new ResponseEntity<String>(versiondata, HttpStatus.OK);
	}

}
