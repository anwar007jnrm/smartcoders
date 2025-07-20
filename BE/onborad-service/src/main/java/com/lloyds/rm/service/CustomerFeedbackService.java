package com.lloyds.rm.service;

import com.lloyds.rm.entity.CustomerFeedback;
import com.lloyds.rm.repository.CustomerFeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFeedbackService {

    private final CustomerFeedbackRepository repository;

    public CustomerFeedbackService(CustomerFeedbackRepository repository) {
        this.repository = repository;
    }

    public CustomerFeedback saveFeedback(CustomerFeedback feedback) {
        return repository.save(feedback);
    }

    public List<CustomerFeedback> getFeedbackByApplicationId(String applicationid) {
        return repository.findByApplicationidOrderByCreateddateDesc(applicationid);
    }
}
