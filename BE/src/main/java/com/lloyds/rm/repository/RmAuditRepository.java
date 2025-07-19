package com.lloyds.rm.repository;

import com.lloyds.rm.entity.RmAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RmAuditRepository extends JpaRepository<RmAudit, Long> {
    List<RmAudit> findByApplicationid(Long applicationId);
}
