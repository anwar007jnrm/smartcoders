package com.lloyds.rm.service;

import com.lloyds.rm.entity.Application;
import com.lloyds.rm.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository repo;

    public ApplicationService(ApplicationRepository repo) {
        this.repo = repo;
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
        return repo.findById(applicationId).get();
    }
}
