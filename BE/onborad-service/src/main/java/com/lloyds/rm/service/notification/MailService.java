package com.lloyds.rm.service.notification;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@Service
public class MailService implements NotificationService {
    private final JavaMailSender mailSender;
    private final SecretKey secretKey;

    public MailService(JavaMailSender mailSender) throws Exception {
        this.mailSender = mailSender;
        this.secretKey = generateSecretKey();
    }

    private SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // AES key size
        return keyGen.generateKey();
    }
    public String encryptParam(String param) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(param.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    @Override
    @Async
    public void sendNotification(String applicationId, String email) throws Exception {
        // Logic to send mail notification

        String resumeLink = "https://example.com/resume?token=" + encryptParam(applicationId);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Resume Journey" + applicationId);
        message.setText("Please resume your journey for applicationId: " + applicationId+
                        ". Click here to resume: " + resumeLink);
        mailSender.send(message);
        System.out.println("Sending  mail notification"+ resumeLink);
        // Here you would integrate with an email service provider to send the actual email
    }
}
