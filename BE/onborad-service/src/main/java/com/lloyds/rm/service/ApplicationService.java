package com.lloyds.rm.service;

import com.lloyds.rm.Util.EncryptionUtil;
import com.lloyds.rm.Util.MaskUtil;
import com.lloyds.rm.entity.Application;
import com.lloyds.rm.entity.ResumeApplication;
import com.lloyds.rm.exception.ServiceException;
import com.lloyds.rm.model.Constants;
import com.lloyds.rm.model.OtpRequest;
import com.lloyds.rm.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
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
        Application app = repo.findById(id).orElseThrow();
        app.setRmid(rmid);
        return repo.save(app);
    }

    public Application createApplication(Application app) {
        return repo.save(app);
    }

    public Application updateApplication(String appid, Application updated) {
        Application existing = repo.findByAppid(appid).orElseThrow();
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

    public ResumeApplication resumeJourney(String token) throws Exception {
        String  applicationId = EncryptionUtil.decrypt(token, secretKey);
        Application app = repo.findByAppid(applicationId).orElseThrow();
        OtpRequest otpRequest = new OtpRequest();
        otpRequest.setMode("sms");
        otpRequest.setRecipient(app.getMobilenumber());
        otpService.generateOtp(otpRequest);
        ResumeApplication resumeApp = new ResumeApplication();
        resumeApp.setApplicationId(app.getId());
        resumeApp.setMessage("OTP sent to your registered mobile number: " + MaskUtil.maskMobileNumber(app.getMobilenumber()));
        return resumeApp;
    }
}
