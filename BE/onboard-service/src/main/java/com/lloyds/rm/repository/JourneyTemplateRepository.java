// src/main/java/com/example/demo/repository/JourneyTemplateRepository.java
package com.lloyds.rm.repository;

import com.lloyds.rm.entity.JourneyTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JourneyTemplateRepository extends JpaRepository<JourneyTemplate, Long> {
}
