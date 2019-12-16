package com.global.services;

import com.global.model.MailNotification;
import com.global.repository.MailNotificationRepository;
import com.global.services.logger.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Mailer {

    @Value("${onriot.mail.send.from}")
    private String mailAddress;

    @Value("${onriot.mail.admin.addresses}")
    private String[] adminAddresses;

    @Autowired
    private MailNotificationRepository mailNotificationRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendInternalMail(String subject, String msg) {
        this.sendMail(this.adminAddresses, "[OnRiot] " + subject, msg);
    }

    public void sendMail(String to, String subject, String message) {
        this.sendMail(new String[]{to}, subject, message);
    }


    public void queueErrorMail(Log log) {
        MailNotification mailNotification = new MailNotification();
        mailNotification.setLog(log);
        this.mailNotificationRepository.save(mailNotification);
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    private void sendQueuedErrorMails() {
        List<MailNotification> mailNotifications = this.mailNotificationRepository.findAll();
        this.mailNotificationRepository.deleteAll();

        String txtMail = "";
        for (MailNotification mailNotification : mailNotifications) {
            txtMail += System.getProperty("line.separator") + "-------------------------------------------";
            txtMail += System.getProperty("line.separator") + ">>>>>" + mailNotification.getLog().getLevel();
            txtMail += System.getProperty("line.separator") + ">>>>>" + mailNotification.getLog().getText();
            txtMail += System.getProperty("line.separator") + "-------------------------------------------";
        }
        this.sendInternalMail(String.format("(%s) warnings and errors", mailNotifications.size()), txtMail);
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
