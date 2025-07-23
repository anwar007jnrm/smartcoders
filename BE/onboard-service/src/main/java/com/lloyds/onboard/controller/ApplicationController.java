package com.lloyds.onboard.controller;

import com.lloyds.onboard.entity.Application;
import com.lloyds.onboard.entity.ResumeApplication;
import com.lloyds.onboard.model.Constants;
import com.lloyds.onboard.model.NotificationType;
import com.lloyds.onboard.service.ApplicationService;
import com.lloyds.onboard.service.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;
    private final Map<String, NotificationService> notificationServices;

    public ApplicationController(ApplicationService service, Map<String, NotificationService> notificationServices) {
        this.service = service;
        this.notificationServices = notificationServices;
    }

    @GetMapping
    public List<Application> getAll(@RequestParam(required = false) String rmid) {
        if (rmid != null && !rmid.isEmpty()) {
            return service.getApplicationsByRmid(rmid);
        }
        return service.getAllApplications();
    }

    @GetMapping("/{applicationId}")
    public Application getApplication(@PathVariable Long applicationId) {
        return service.getApplication(applicationId);
    }

    @PostMapping
    public Application create(@RequestBody Application app) {
        return service.createApplication(app);
    }

    @PutMapping
    public Application saveAndContinue(@RequestBody Application app, @RequestHeader Map<String, String> headers) throws Exception {
        String sessionStatus = headers.get(Constants.SESSION_STATUS);
        if (sessionStatus == null || !sessionStatus.equals(Constants.IN_PROGRESS)) {
            notificationServices.get(NotificationType.MAIL.getServiceName()).sendNotification(app.getAppid(), app.getEmail());
           // notificationServices.get(NotificationType.SMS.getServiceName()).sendNotification(app.getAppid(), app.getMobilenumber());
        }
        return service.updateApplication(app.getAppid(), app);
    }

    @PostMapping("/submit-application")
    public ResponseEntity<String> submitApplication(@RequestBody Application app) {
        service.updateApplication(app.getAppid(), app);
        return ResponseEntity.ok("Application submitted successfully with ID: " + app.getAppid());
    }

    @GetMapping("/resume-journey")
    public ResponseEntity<ResumeApplication> resumeJourney(@RequestParam(value = "token") String token) throws Exception {
        return ResponseEntity.ok(service.resumeJourney(token));
    }

    @PutMapping("/{applicationId}/assign")
    public Application assignRM(@PathVariable Long applicationId, @RequestBody Map<String,String> req) {
        return service.assignToRM(applicationId, req.get("assignedTo"));
    }

    @PutMapping("/{appid}")
    public Application update(@PathVariable String appid, @RequestBody Application updated) {
        return service.updateApplication(appid, updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteApplication(id);
    }



    public static class AssignRequest {
        private String assignedTo;
        public String getRmid() { return assignedTo; }
        public void setRmid(String rmid) { this.assignedTo = rmid; }
    }
}
