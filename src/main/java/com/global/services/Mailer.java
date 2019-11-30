package com.global.services;

import com.global.services.logger.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Mailer {

    @Value("${mail.send.from}")
    private String mailAddress;

    @Value("${mail.admin.addresses}")
    private String[] adminAdresses;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendInternalMail(String msg) {
        this.sendMail(this.adminAdresses, "IMPORTANT INTERNAL MESSAGE", msg);
    }

    public void sendMail(String to, String subject, String message) {
        this.sendMail(new String[]{to}, subject, message);
    }


    public void sendErrorMail(LogLevel level, String message) {
        this.sendMail(this.adminAdresses, level.toString(), message);
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
