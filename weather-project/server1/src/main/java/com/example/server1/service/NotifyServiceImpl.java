package com.example.server1.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class NotifyServiceImpl implements Notify{
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyServiceImpl.class);
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmailForNotifyAdmin(String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("alirezafarahani888@gmail.com");
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Error sending email: " + e.getMessage());
        }
    }
}
