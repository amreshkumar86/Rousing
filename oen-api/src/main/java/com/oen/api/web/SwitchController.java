package com.oen.api.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/switches")
public class SwitchController {
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getUserProfileData(HttpServletRequest request,
			@RequestParam("U") String id) {
		String response  = "<MODE=0&U="+id+"&DATA=\\01\\0b\\>" ;
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
