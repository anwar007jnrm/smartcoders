package com.lloyds.onboard.service.notification;

public interface NotificationService {
    void sendNotification(String applicationId, String emailOrMobile) throws Exception;
}
