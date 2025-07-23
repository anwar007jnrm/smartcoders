package com.lloyds.onboard.controller;

import com.lloyds.onboard.Util.DateTimeUtil;
import com.lloyds.onboard.repository.OtpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class OtpCleanupScheduler {

    private final OtpRepository otpRepository;

    public OtpCleanupScheduler(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    // Run once a day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteOldOtps() {
        log.info("Start delete old Otps");
        LocalDateTime cutofffDate = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
        otpRepository.deleteOtpsOlderThan(DateTimeUtil.format(cutofffDate));
    }
}