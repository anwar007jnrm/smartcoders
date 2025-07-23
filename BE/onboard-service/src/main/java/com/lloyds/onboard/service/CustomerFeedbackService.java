package com.lloyds.onboard.service;

import com.lloyds.onboard.entity.CustomerFeedback;
import com.lloyds.onboard.repository.CustomerFeedbackRepository;
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

    public List<CustomerFeedback> getFeedbackByApplicationId(Long applicationid) {
        return repository.findByApplicationidOrderByCreateddateDesc(applicationid);
    }
}
