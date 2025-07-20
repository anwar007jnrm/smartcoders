package com.lloyds.rm.controller;

import com.lloyds.rm.entity.Application;
import com.lloyds.rm.entity.ResumeApplication;
import com.lloyds.rm.model.Constants;
import com.lloyds.rm.model.NotificationType;
import com.lloyds.rm.service.ApplicationService;
import com.lloyds.rm.service.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
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
    public Application saveAndContinue(@RequestBody Application app, @RequestHeader Map<String, String> headers, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        String sessionStatus = headers.get(Constants.SESSION_STATUS);
        if (sessionStatus == null || !sessionStatus.equals(Constants.IN_PROGRESS)) {
            notificationServices.get(NotificationType.MAIL.getServiceName()).sendNotification(app.getAppid(), app.getEmail());
           // notificationServices.get(NotificationType.SMS.getServiceName()).sendNotification(app.getAppid(), app.getMobilenumber());
            if (sessionStatus.equals(Constants.TERMINATED)){
                return app;
            }
        }
        return service.updateApplication(app.getAppid(), app);
    }

    @PostMapping("/submit-application")
    public ResponseEntity<String> submitApplication(@RequestBody Application app) {
        service.updateApplication(app.getAppid(), app);
        return ResponseEntity.ok("Application submitted successfully with ID: " + app.getAppid());
    }

    /*public ResumeApplication resumeJourney(@RequestParam String token) {
        Application app = service.getApplication(Long.parseLong(applicationId));
        if (app == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(app);
    }*/

    @PostMapping(value = "/{applicationId}/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadDocuments(@RequestParam("file") MultipartFile file, @RequestParam("applicationId") String applicationId) {
        // Logic to handle file upload
        // For example, save the file to a storage service or database
        // Here we just return a success message for demonstration purposes
        return ResponseEntity.ok("File uploaded successfully for application ID: " + applicationId);
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
