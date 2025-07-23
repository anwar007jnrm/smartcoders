package com.lloyds.onboard.service.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService implements NotificationService {

    @Value("${2factor.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public void sendNotification(String applicationId, String mobile, String firstname, String journeytype) {
        // Logic to send SMS notification
        String resumeUrl = "https://example.com/resume-journey/" + applicationId;
        String url = "https://2factor.in/API/V1/" + apiKey + "/SMS/" + mobile + "/" + resumeUrl;
        restTemplate.getForObject(url, String.class);
        System.out.println("Sending sms notification");
        // Here you would integrate with an SMS service provider to send the actual SMS
    }
}
