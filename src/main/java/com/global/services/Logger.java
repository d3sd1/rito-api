package com.global.services;

import com.global.services.logger.Log;
import com.global.services.logger.LogLevel;
import com.global.services.logger.LogRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("LogService")
@Transactional
public class Logger {
    @Autowired
    private LogRepository logRepository;

    @Autowired
    private Mailer mailer;

    @Autowired
    private Logger logger;

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

    public void debug(String msg) {
        org.slf4j.Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.info(msg);
    }

    public void info(String msg) {
        org.slf4j.Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.info(msg);
        Log log = new Log();
        log.setLevel(LogLevel.INFO);
        log.setText(msg);
        this.logRepository.save(log);
    }

    public void warning(String msg) {
        org.slf4j.Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.warn(msg);
        Log log = new Log();
        log.setLevel(LogLevel.WARNING);
        log.setText(msg);
        this.mailer.sendErrorMail(LogLevel.WARNING, msg);
        this.logRepository.save(log);
    }

    public void error(Exception e) {
        this.error(ExceptionUtils.getStackTrace(e));
    }

    public void error(String msg) {
        org.slf4j.Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.error(msg);
        Log log = new Log();
        log.setLevel(LogLevel.ERROR);
        log.setText(msg);
        this.mailer.sendErrorMail(LogLevel.ERROR, msg);
        this.logRepository.save(log);
    }

}
