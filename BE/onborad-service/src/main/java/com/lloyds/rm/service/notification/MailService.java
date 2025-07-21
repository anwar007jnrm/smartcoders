package com.lloyds.rm.service.notification;

import com.lloyds.rm.Util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService implements NotificationService {
    private final JavaMailSender mailSender;

    @Value("${encryption.key}")
    private String secretKey;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendNotification(String applicationId, String email) throws Exception {
        // Logic to send mail notification

        String resumeLink = "http://localhost:3000/resumeJourney?token=" + EncryptionUtil.encrypt(applicationId, secretKey);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Resume Journey " + applicationId);
        message.setText("Please resume your journey for applicationId: " + applicationId+
                        ". Click here to resume: " + resumeLink);
        mailSender.send(message);
        System.out.println("Sending  mail notification"+ resumeLink);
        // Here you would integrate with an email service provider to send the actual email
    }
}
