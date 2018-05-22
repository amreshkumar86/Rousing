package com.oen.api.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oen.core.config.UserResponseMessage;
import com.oen.core.domain.JwtUser;
import com.oen.core.domain.SuccessResponse;
import com.oen.core.domain.model.UserFeedback;
import com.oen.core.service.FeedbackService;
import com.oen.core.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Value("${token.header}")
    private String tokenHeader;
	
	@Autowired
	private UserService userService;
	
	private SuccessResponse response = null;
	
	@RequestMapping(value = "/get/my/profile", 
			method = RequestMethod.POST ,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserProfileData(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		JwtUser profileData = userService.getUserDetails(token);
		return new ResponseEntity<JwtUser>(profileData, HttpStatus.OK);
	}

}
