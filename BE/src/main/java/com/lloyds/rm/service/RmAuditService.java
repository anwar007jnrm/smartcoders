package com.lloyds.rm.service;

import com.lloyds.rm.entity.RmAudit;
import com.lloyds.rm.repository.RmAuditRepository;
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
