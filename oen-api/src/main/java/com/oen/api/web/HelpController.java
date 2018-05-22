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
import com.oen.core.domain.SuccessResponse;
import com.oen.core.domain.model.UserFeedback;
import com.oen.core.service.FeedbackService;


@RestController
@RequestMapping("/help")
public class HelpController {
	
	
	@Value("${token.header}")
    private String tokenHeader;
	
	@Autowired
	private FeedbackService feedbackService;
	
	private SuccessResponse response =null;
	
	
	@RequestMapping(value = "/contact_us", 
			method = RequestMethod.POST ,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createCampaign(HttpServletRequest request, 
		@RequestBody UserFeedback feedbackObj) {
		String token = request.getHeader(tokenHeader);
		feedbackService.saveUserFeedback(token, feedbackObj);
		response = new SuccessResponse(HttpStatus.OK.value(), 
				UserResponseMessage.FEEDBACK_REACHED_MESSAGE);
		return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
	}

}
