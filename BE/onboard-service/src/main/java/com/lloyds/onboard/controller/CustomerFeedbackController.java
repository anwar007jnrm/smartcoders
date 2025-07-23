package com.lloyds.onboard.controller;

import com.lloyds.onboard.entity.CustomerFeedback;
import com.lloyds.onboard.service.CustomerFeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customerfeedback")
public class CustomerFeedbackController {

    private final CustomerFeedbackService service;

    public CustomerFeedbackController(CustomerFeedbackService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerFeedback submitFeedback(@RequestBody CustomerFeedback feedback) {
        return service.saveFeedback(feedback);
    }

    @GetMapping("/{applicationid}")
    public List<CustomerFeedback> getFeedbackByAppId(@PathVariable Long applicationid) {
        return service.getFeedbackByApplicationId(applicationid);
    }
}
