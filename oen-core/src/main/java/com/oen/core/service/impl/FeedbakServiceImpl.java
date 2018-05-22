package com.oen.core.service.impl;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.oen.core.config.OenConstants;
import com.oen.core.domain.model.User;
import com.oen.core.domain.model.UserFeedback;
import com.oen.core.domain.repository.UserFeedbackRepository;
import com.oen.core.service.FeedbackService;
import com.oen.core.service.MailEngine;
import com.oen.core.service.UserService;

@Service("feedbackService")
public class FeedbakServiceImpl implements FeedbackService {
	
	@Value("${contact.us.subject}")
	private String CONTACT_US_MAIL_SUBJECT;
	
	@Value("${oen.admin.mail.id}")
	private String ADMIN_MAIL_ID;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserFeedbackRepository userFeedbackRepository;
	
	@Autowired
	MailEngine mailEngine;
	

	@Override
	public UserFeedback saveUserFeedback(String token, UserFeedback feedbackObj) {
		User loggedInUser = userService.getUserByToken(token);
		feedbackObj.setFeedbackGivenBy(loggedInUser);
		feedbackObj.setStatus(OenConstants.PENDING_STATUS);
		feedbackObj.setGivenOn(LocalDateTime.now());
		feedbackObj = userFeedbackRepository.saveAndFlush(feedbackObj);
		mailEngine.sendEmail(frameFeedbackMailObject(feedbackObj));
		return feedbackObj;
	}
	
	
	private SimpleMailMessage frameFeedbackMailObject(UserFeedback feedbackObj) {
		SimpleMailMessage message = new SimpleMailMessage();
		User user = feedbackObj.getFeedbackGivenBy();
        message.setFrom(user.getEmail());
//        message.setFrom("akvishu52@gmail.com");
        message.setTo(ADMIN_MAIL_ID);
        message.setSubject(CONTACT_US_MAIL_SUBJECT+" "+user.getFirstname()+" "+user.getLastname());
        String content  = "Hi  \n\n"
        		        + ""+user.getFirstname()+" "+user.getLastname()+" has sent a enquiry through the OEn app.\n\n"
		        		+ "Subject: "+feedbackObj.getTitle()+"\n\n"
		        		+ "Content: "+feedbackObj.getQuery()+"\n\n"
        		        + "Regards,\n"
        		        + "Team OEn.";
        message.setText(content);
		return message;
	}
	

}
