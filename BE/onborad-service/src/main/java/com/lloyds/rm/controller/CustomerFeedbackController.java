package com.lloyds.rm.controller;

import com.lloyds.rm.entity.CustomerFeedback;
import com.lloyds.rm.service.CustomerFeedbackService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customerfeedback")
@CrossOrigin(origins = "*")
public class CustomerFeedbackController {

    private final CustomerFeedbackService service;

    public CustomerFeedbackController(CustomerFeedbackService service) {
        this.service = service;
    }

  /*  @PostMapping
    public CustomerFeedback submitFeedback(@RequestBody CustomerFeedback feedback) {
        return service.saveFeedback(feedback);
    }*/

    @GetMapping("/{applicationid}")
    public List<CustomerFeedback> getFeedbackByAppId(@PathVariable String applicationid) {
        return service.getFeedbackByApplicationId(applicationid);
    }
}
