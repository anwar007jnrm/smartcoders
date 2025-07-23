package com.lloyds.rm.repository;

import com.lloyds.rm.entity.Application;
import com.lloyds.rm.entity.ApplicationDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationDocumentRepository extends JpaRepository<ApplicationDocument, Long> {
}
