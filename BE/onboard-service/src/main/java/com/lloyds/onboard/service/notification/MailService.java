package com.lloyds.onboard.service.notification;

import com.lloyds.onboard.Util.EncryptionUtil;
import com.lloyds.onboard.exception.ServiceException;
import com.lloyds.onboard.model.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService implements NotificationService {
    private final JavaMailSender mailSender;

    @Value("${encryption.key}")
    private String secretKey;
    @Value("${resume-journey.url}")
    private String resumeUrl;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendNotification(String applicationId, String email) throws ServiceException {
        // Logic to send mail notification
        String token;
        try {
            token = EncryptionUtil.encrypt(applicationId, secretKey);
        } catch (Exception e) {
            log.error("Error encrypting applicationId", e);
            throw new ServiceException(Constants.ENCRYPTION_ERROR);
        }
        String resumeLink = resumeUrl + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Resume Journey " + applicationId);
        message.setText("Please resume your journey for applicationId: " + applicationId+
                        ". Click here to resume: " + resumeLink);
        log.info("Sending  mail notification"+ resumeLink);
        try {
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Error sending mail notification", e);
            throw new ServiceException(Constants.RESUME_JOURNEY_ERROR);
        }
    }
}
