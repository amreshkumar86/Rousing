package com.oen.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.oen.core.service.MailEngine;


/**
 * Class for sending e-mail messages based on Velocity templates or with
 * attachments.
 */
@Service("mailEngine")
public class MailEngineImpl implements MailEngine {
	
	@Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void sendEmail(SimpleMailMessage message) {
        mailSender.send(message);
    }
	
}
