package com.lloyds.rm.model;

import lombok.Data;

@Data
public class OtpRequest {
    private String recipient; // mobile or email
    private String mode;      // "sms" or "email"

    // Getters and Setters
}
