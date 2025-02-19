package com.ezotex.comm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	public void sendEmail(String to, String title, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("EzoTex@gmail.com");
		message.setTo(to);
		message.setSubject(title);
		message.setText(content);
		emailSender.send(message);
	}
}
