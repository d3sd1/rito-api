package com.global.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Mailer {

    @Value("${onriot.mail.send.from}")
    private String mailAddress;

    @Value("${onriot.mail.admin.addresses}")
    private String[] adminAddresses;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendInternalMail(String subject, String msg) {
        this.sendMail(this.adminAddresses, "[OnRiot] " + subject, msg);
    }

    public void sendMail(String to, String subject, String message) {
        this.sendMail(new String[]{to}, subject, message);
    }


    public void sendMail(String[] to, String subject, String message) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(this.mailAddress);
            msg.setTo(to);

            msg.setSubject(subject);
            msg.setText(message);

            javaMailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
