package com.lloyds.rm.model;

public enum NotificationType {
    SMS, MAIL, SMS_AND_MAIL;
    
    public String getServiceName() {
        return this.name().toLowerCase() + "Service";
    }
}
