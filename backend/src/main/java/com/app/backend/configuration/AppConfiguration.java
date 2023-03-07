package com.app.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.validation.Valid;
import java.util.Properties;

@Configuration
public class AppConfiguration {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String startTlsValue;

    @Value("${spring.mail.port}")
    private String port;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        /* System.out.println("HOST IS : "+ this.host);
        System.out.println("USERNAME IS : "+ this.username);
        System.out.println("PASSWORD IS : "+ this.password);
        System.out.println("PORT IS : "+ this.port); */

        mailSender.setHost(this.host);
        mailSender.setPort(Integer.parseInt(this.port));

        mailSender.setUsername(this.username);
        mailSender.setPassword(this.password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", this.auth);
        props.put("mail.smtp.starttls.enable", this.startTlsValue);
        props.put("mail.debug", "true");

        return mailSender;
    }
}
