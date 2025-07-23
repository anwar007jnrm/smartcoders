package com.lloyds.rm.controller;

import com.lloyds.rm.entity.RmUser;
import com.lloyds.rm.repository.RmUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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
        Optional<RmUser> user = rmUserRepository.findByRmidAndPassword(rmId, password);

        if (user.isPresent()) {
            return Map.of("name", user.get().getName(), "role", user.get().getRole().name());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
