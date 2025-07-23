package com.lloyds.onboard.service;

import com.lloyds.onboard.entity.RmAudit;
import com.lloyds.onboard.repository.RmAuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RmAuditService {

  private final RmAuditRepository repo;

  public RmAuditService(RmAuditRepository repo) {
    this.repo = repo;
  }

  public RmAudit createAudit(RmAudit audit) {
    return repo.save(audit);
  }

  public List<RmAudit> getAuditHistory(Long applicationId) {
    return repo.findByApplicationid(applicationId);
  }

}
