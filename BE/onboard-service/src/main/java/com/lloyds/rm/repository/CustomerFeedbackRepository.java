package com.lloyds.rm.repository;

import com.lloyds.rm.entity.CustomerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerFeedbackRepository extends JpaRepository<CustomerFeedback, Long> {
    List<CustomerFeedback> findByApplicationidOrderByCreateddateDesc(Long applicationid);
}
