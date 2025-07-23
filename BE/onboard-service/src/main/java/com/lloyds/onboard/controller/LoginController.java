package com.lloyds.onboard.controller;

import com.lloyds.onboard.entity.RmUser;
import com.lloyds.onboard.exception.ServiceException;
import com.lloyds.onboard.model.Constants;
import com.lloyds.onboard.repository.RmUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j// Replace with frontend port
public class LoginController {

    @Autowired
    private RmUserRepository rmUserRepository;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
        String rmId = loginRequest.get("username");
        String password = loginRequest.get("password");
        log.info(rmId);
        log.info(password);
        RmUser user = rmUserRepository.findByRmidAndPassword(rmId, password).orElseThrow(() -> new ServiceException(Constants.INVALID_CREDENTIALS));
        return Map.of("name", user.getName(), "role", user.getRole().name());
    }
}
