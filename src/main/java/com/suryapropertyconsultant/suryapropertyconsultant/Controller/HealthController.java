package com.suryapropertyconsultant.suryapropertyconsultant.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Example simple health endpoint
@RestController
public class HealthController {

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
