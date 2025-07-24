package com.lloyds.onboard.model;

import lombok.Data;

@Data
public class OtpVerifyRequest {
    private String recipient;
    private String otp;
    private Long id;

    // Getters and Setters
}