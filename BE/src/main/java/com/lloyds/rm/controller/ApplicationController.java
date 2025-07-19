package com.lloyds.rm.controller;

import com.lloyds.rm.entity.Application;
import com.lloyds.rm.service.ApplicationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
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
