package com.projects.completespringbanking.service.impl;

import com.projects.completespringbanking.dto.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setText(emailDetails.getMessageBody());
            simpleMailMessage.setSubject("SpringBankingAlert");

            javaMailSender.send(simpleMailMessage);
            System.out.println("Mail sent successfully");
        }
        catch (Exception e){
            throw new RuntimeException();
//            System.out.println("Exception:" + e);
        }
    }
}
