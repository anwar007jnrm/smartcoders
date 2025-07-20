package com.lloyds.rm.service.notification;

public interface NotificationService {
    void sendNotification(String applicationId, String emailOrMobile) throws Exception;
}
