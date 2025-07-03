//USING SMTP GOOGLE
package com.suryapropertyconsultant.suryapropertyconsultant.Controller;

import com.suryapropertyconsultant.suryapropertyconsultant.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/contact")
    public ResponseEntity<?> contactSupport(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String message = payload.get("message");

        String result = emailService.sendSupportEmail(email, message);
        boolean success = result.toLowerCase().contains("success");

        return ResponseEntity.ok(Map.of(
                "status", success ? "sent" : "error",
                "info", result
        ));
    }
}

//
//package com.suryapropertyconsultant.suryapropertyconsultant.Controller;
//
//import com.suryapropertyconsultant.suryapropertyconsultant.Service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//
//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//@RequestMapping("/api/support")
//public class SupportController {
//
//    @Autowired
//    private EmailService emailService;
//@PostMapping("/contact")
//public ResponseEntity<?> contactSupport(@RequestBody Map<String, String> payload) {
//    try {
//        String email = payload.get("email");
//        String message = payload.get("message");
//        String result = emailService.sendSupportEmail(email, message);
//        boolean success = result.toLowerCase().contains("success");
//
//        return ResponseEntity.ok(Map.of(
//                "status", success ? "sent" : "error",
//                "info", result
//        ));
//    } catch (Exception e) {
//        e.printStackTrace(); // See console
//        return ResponseEntity.status(500).body(Map.of(
//                "status", "error",
//                "info", "Exception: " + e.getMessage()
//        ));
//    }
//}
//
//}
//
