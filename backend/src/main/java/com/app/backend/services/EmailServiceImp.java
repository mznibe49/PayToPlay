package com.app.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImp {

    @Autowired
    private JavaMailSender emailSender;

    public void sendForgottenPassWordEmail(String email, String resetLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("noreply@P2P.com", "team support");
        helper.setTo(email);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>" +
                "<p>You have requested to reset your password.</p>" +
                "<p>Click the link below to change your password : </p>" +
                "<p>(This link is available only 24 hours) => <b><a href=\"" + resetLink + "\">Change my password</a></b></p>" +
                "<p>Ignore this email if you do remember your password, or you have node made the request</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        emailSender.send(message);
    }

   /* public void sendSimpleMessage(
            String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    } */
}
