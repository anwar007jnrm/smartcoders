package com.lloyds.onboard.model;

import lombok.Data;

@Data
public class OtpVerifyRequest {
    private String recipient;
    private String otp;

    // Getters and Setters
}