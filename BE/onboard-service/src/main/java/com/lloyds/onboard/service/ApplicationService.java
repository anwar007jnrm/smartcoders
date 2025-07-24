package com.lloyds.onboard.service;

import com.lloyds.onboard.Util.EncryptionUtil;
import com.lloyds.onboard.Util.MaskUtil;
import com.lloyds.onboard.entity.Application;
import com.lloyds.onboard.entity.ResumeApplication;
import com.lloyds.onboard.exception.ServiceException;
import com.lloyds.onboard.model.Constants;
import com.lloyds.onboard.model.OtpRequest;
import com.lloyds.onboard.repository.ApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ApplicationService {

    private final ApplicationRepository repo;
    private final OtpService otpService;

    @Value("${encryption.key}")
    private String secretKey;

    public ApplicationService(ApplicationRepository repo, OtpService otpService) {
        this.repo = repo;
        this.otpService = otpService;
    }

    public List<Application> getAllApplications() {
        return repo.findAll();
    }

    public List<Application> getApplicationsByRmid(String rmid) {
        return repo.findByRmid(rmid);
    }

    public Application assignToRM(Long id, String rmid) {
        Application app = repo.findById(id).orElseThrow(() -> new ServiceException(Constants.APPLICATION_ID_NOT_FOUND));
        app.setRmid(rmid);
        return repo.save(app);
    }

    public Application createApplication(Application app) {
        return repo.save(app);
    }

    public Application updateApplication(String appid, Application updated) {
        Application existing = repo.findByAppid(appid).orElseThrow(() -> new ServiceException(Constants.APPLICATION_ID_NOT_FOUND));
        updated.setId(existing.getId());
        updated.setCreateddate(existing.getCreateddate());
        updated.setUpdateddate(LocalDateTime.now());
        return repo.save(updated);
    }

    public void deleteApplication(Long id) {
        repo.deleteById(id);
    }

    public Application getApplication(Long applicationId) {
        return repo.findById(applicationId).orElseThrow(() -> new ServiceException(Constants.APPLICATION_ID_NOT_FOUND));
    }

    public ResumeApplication resumeJourney(String token) throws ServiceException {
        String applicationId;
        try {
            applicationId = EncryptionUtil.decrypt(token, secretKey);
        } catch (Exception e) {
            log.error("Error decrypting applicationId", e);
            throw new ServiceException(Constants.DECRYPTION_ERROR);
        }
        Application app = repo.findByAppid(applicationId).orElseThrow(() -> new ServiceException(Constants.APPLICATION_ID_NOT_FOUND));
        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setMode("sms");
        otpRequest.setRecipient(app.getMobilenumber());
        otpService.generateOtp(otpRequest);
        ResumeApplication resumeApp = new ResumeApplication();
        resumeApp.setId(app.getId());
        resumeApp.setMessage("OTP sent to your registered mobile number: " + MaskUtil.maskMobileNumber(app.getMobilenumber()));
        return resumeApp;
    }
}
