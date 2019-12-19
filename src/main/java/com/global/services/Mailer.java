/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Mailer service. Used for sending emails.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Service
public class Mailer {

    @Value("${onriot.mail.send.from}")
    private String mailAddress;

    @Value("${onriot.mail.admin.addresses}")
    private String[] adminAddresses;
    private JavaMailSender javaMailSender;

    public Mailer(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Send internal mail.
     *
     * @param subject the subject
     * @param msg     the msg
     * @author d3sd1
     */
    public void sendInternalMail(String subject, String msg) {
        this.sendMail(this.adminAddresses, "[OnRiot] " + subject, msg);
    }

    /**
     * Send mail.
     *
     * @param to      the to
     * @param subject the subject
     * @param message the message
     * @author d3sd1
     */
    public void sendMail(String to, String subject, String message) {
        this.sendMail(new String[]{to}, subject, message);
    }


    /**
     * Send mail.
     *
     * @param to      the to
     * @param subject the subject
     * @param message the message
     */
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
