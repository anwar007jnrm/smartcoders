package com.lloyds.rm.service;

import com.lloyds.rm.entity.Otp;
import com.lloyds.rm.model.OtpRequest;
import com.lloyds.rm.model.OtpVerifyRequest;
import com.lloyds.rm.repository.OtpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class OtpService {

    @Value("${2factor.api.key}")
    private String apiKey;

    @Value("${otp.expiry.minutes:5}")
    private int expiryMinutes;

    private final OtpRepository otpRepository;
    private final JavaMailSender mailSender;
    private final RestTemplate restTemplate = new RestTemplate();

    public OtpService(OtpRepository otpRepository, JavaMailSender mailSender) {
        this.otpRepository = otpRepository;
        this.mailSender = mailSender;
    }

    public String generateOtp(OtpRequest request) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        if ("sms".equalsIgnoreCase(request.getMode())) {
            sendOtpViaSms(request.getRecipient(), otp);
        } else if ("email".equalsIgnoreCase(request.getMode())) {
            sendOtpViaEmail(request.getRecipient(), otp);
        } else {
            return "Invalid mode. Use 'sms' or 'email'";
        }

        Otp entity = new Otp();
        entity.setRecipient(request.getRecipient());
        entity.setOtp(otp);
        entity.setMode(request.getMode());
        entity.setUsed(false);
        entity.setCreatedate(LocalDateTime.now());

        otpRepository.save(entity);

        return "OTP sent successfully via " + request.getMode();
    }

    private void sendOtpViaSms(String mobile, String otp) {
        String url = "https://2factor.in/API/V1/" + apiKey + "/SMS/" + mobile + "/" + otp;
        log.info("2factor url" + url);
        restTemplate.getForObject(url, String.class);
    }

    private void sendOtpViaEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }

    public String verifyOtp(OtpVerifyRequest request) {
        Optional<Otp> latestOtp = otpRepository.findTopByRecipientOrderByCreatedateDesc(request.getRecipient());

        if (latestOtp.isEmpty()) return "OTP not found.";

        Otp otp = latestOtp.get();

        if (otp.isUsed()) return "OTP already used.";
        if (!otp.getOtp().equals(request.getOtp())) return "Invalid OTP.";

        LocalDateTime expiry = otp.getCreatedate().plusMinutes(expiryMinutes);
        if (LocalDateTime.now().isAfter(expiry)) return "OTP expired.";

        otp.setUsed(true);
        otpRepository.save(otp);

        return "OTP verified successfully.";
    }
}
