package com.lloyds.onboard.controller;

import com.lloyds.onboard.model.OtpRequest;
import com.lloyds.onboard.model.OtpVerifyRequest;
import com.lloyds.onboard.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateOtp(@RequestBody OtpRequest request) {
        String result = otpService.generateOtp(request);
        boolean success = result.contains("successfully");
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody OtpVerifyRequest request) {
        String result = otpService.verifyOtp(request);
        boolean success = result.equalsIgnoreCase("OTP verified successfully.");
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", result);
        return ResponseEntity.ok(response);
    }
}

