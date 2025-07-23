package com.lloyds.rm.controller;

import com.lloyds.rm.entity.Application;
import com.lloyds.rm.entity.RmAudit;
import com.lloyds.rm.service.RmAuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rmaudit")
@Slf4j
public class RmAuditController {

    private final RmAuditService service;

    public RmAuditController(RmAuditService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RmAudit> logAction(@RequestBody RmAudit audit) {
        return ResponseEntity.ok(service.createAudit(audit));
    }

    @GetMapping("/{applicationId}")
    public List<RmAudit> getApplication(@PathVariable Long applicationId) {
        return service.getAuditHistory(applicationId);
    }
}
