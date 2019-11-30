package status.disabled.fetcher.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("LogService")
@Transactional
public class LogService {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private LogService logger;

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
            Logger consoleLogger = LoggerFactory.getLogger(this.getClass());
            consoleLogger.error("CRITICAL ERROR ON LOG SERVICE!!! CHECK BELOW");
            e.printStackTrace();
            return this.getClass();
        }
    }

    void sendEmail(String type, String log) {

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("bugs@onlol.net");
            msg.setTo("andreigarciacuadra@gmail.com");

            msg.setSubject("[" + type + "] OnLOL Scraper");
            msg.setText(log);

            javaMailSender.send(msg);
        } catch (Exception e) {
            this.logger.error("Could not send email [" + type + "]" + log + " with exception " + e.getMessage());
        }

    }

    public void debug(String msg) {
        Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.info(msg);
    }

    public void info(String msg) {
        Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.info(msg);
        Log log = new Log();
        log.setLevel(LogLevel.INFO);
        log.setText(msg);
        this.logRepository.save(log);
    }

    public void warning(String msg) {
        Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.warn(msg);
        Log log = new Log();
        log.setLevel(LogLevel.WARNING);
        log.setText(msg);
        this.sendEmail("WARNING", msg);
        this.logRepository.save(log);
    }

    public void error(String msg) {
        Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
        consoleLogger.error(msg);
        Log log = new Log();
        log.setLevel(LogLevel.ERROR);
        log.setText(msg);
        this.sendEmail("ERROR", msg);
        this.logRepository.save(log);
    }

}
