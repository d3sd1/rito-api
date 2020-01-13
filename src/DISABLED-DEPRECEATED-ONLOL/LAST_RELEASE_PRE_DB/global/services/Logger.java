/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.services;

import global.model.MailNotification;
import global.repository.MailNotificationRepository;
import global.services.logger.Log;
import global.services.logger.LogLevel;
import global.services.logger.LogRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * The type Logger.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Service("LogService")
@Transactional
public class Logger {
    private LogRepository logRepository;
    private Mailer mailer;
    private MailNotificationRepository mailNotificationRepository;

    public Logger(LogRepository logRepository, Mailer mailer, MailNotificationRepository mailNotificationRepository) {
        this.logRepository = logRepository;
        this.mailer = mailer;
        this.mailNotificationRepository = mailNotificationRepository;
    }

    private Class<?> getCaller() {
        try {
            Class<?> clazz = this.getClass();
            for (StackTraceElement trace : Thread.currentThread().getStackTrace()) {
                if (trace.getClassName().indexOf("TecLand.") != -1 && trace.getClassName().indexOf("TecLand.Logger") == -1) {
                    clazz = Class.forName(trace.getClassName());
                    break;
                }
            }
            return clazz;
        } catch (Exception e) {
            org.slf4j.Logger consoleLogger = LoggerFactory.getLogger(this.getClass());
            consoleLogger.error("CRITICAL ERROR ON LOG SERVICE!!! CHECK BELOW");
            e.printStackTrace();
            return this.getClass();
        }
    }

    /**
     * Debug.
     *
     * @param msg the msg
     */
    public void debug(String msg) {
        org.slf4j.Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.info(msg);
    }

    /**
     * Info.
     *
     * @param msg the msg
     */
    public void info(String msg) {
        org.slf4j.Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.info(msg);
        Log log = new Log();
        log.setLevel(LogLevel.INFO);
        log.setText(msg);
        this.logRepository.save(log);
    }

    /**
     * Warning.
     *
     * @param msg the msg
     */
    public void warning(String msg) {
        org.slf4j.Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.warn(msg);
        Log log = new Log();
        log.setLevel(LogLevel.WARNING);
        log.setText(msg);
        this.queueErrorMail(log);
        this.logRepository.save(log);
    }

    /**
     * Error.
     *
     * @param e the e
     */
    public void error(Exception e) {
        this.error(ExceptionUtils.getStackTrace(e));
    }

    /**
     * Error.
     *
     * @param msg the msg
     */
    public void error(String msg) {
        org.slf4j.Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.error(msg);
        Log log = new Log();
        log.setLevel(LogLevel.ERROR);
        log.setText(msg);
        this.queueErrorMail(log);
        this.logRepository.save(log);
    }

    /**
     * Queue error mail.
     *
     * @param log the log
     */
    public void queueErrorMail(Log log) {
        MailNotification mailNotification = new MailNotification();
        mailNotification.setLog(log);
        this.mailNotificationRepository.save(mailNotification);
    }

    /**
     * Send mails every 60s, to prevent spamming when app starts to fail.
     *
     * @author d3sd1
     */

    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    protected void sendQueuedErrorMails() {
        List<MailNotification> mailNotifications = this.mailNotificationRepository.findAll();
        this.mailNotificationRepository.deleteAll();

        String txtMail = "";
        for (MailNotification mailNotification : mailNotifications) {
            txtMail += System.getProperty("line.separator") + "-------------------------------------------";
            txtMail += System.getProperty("line.separator") + ">>>>>" + mailNotification.getLog().getLevel();
            txtMail += System.getProperty("line.separator") + ">>>>>" + mailNotification.getLog().getText();
            txtMail += System.getProperty("line.separator") + "-------------------------------------------";
        }
        if (mailNotifications.size() > 0) {
            this.mailer.sendInternalMail(String.format("(%s) warnings and errors", mailNotifications.size()), txtMail);
        }
    }

}
