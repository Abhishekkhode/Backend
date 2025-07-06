//USING SMTP GOOGLE (Original - Working)
//package com.suryapropertyconsultant.suryapropertyconsultant.Controller;
//
//import com.suryapropertyconsultant.suryapropertyconsultant.Service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/support")
//public class SupportController {
//
//    @Autowired
//    private EmailService emailService;
//
//    @PostMapping("/contact")
//    public ResponseEntity<?> contactSupport(@RequestBody Map<String, String> payload) {
//        String email = payload.get("email");
//        String message = payload.get("message");
//
//        String result = emailService.sendSupportEmail(email, message);
//        boolean success = result.toLowerCase().contains("success");
//
//        return ResponseEntity.ok(Map.of(
//                "status", success ? "sent" : "error",
//                "info", result
//        ));
//    }
//}







// Using MailJet
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






//Using MailJet for both Contact Page and Admin Login Page
package com.suryapropertyconsultant.suryapropertyconsultant.Controller;

import com.suryapropertyconsultant.suryapropertyconsultant.Dto.ContactFormRequest;
import com.suryapropertyconsultant.suryapropertyconsultant.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000") // Ensure this matches your frontend URL
@RestController
@RequestMapping("/api/support")
public class SupportController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/contact")
    public ResponseEntity<?> contactSupport(@RequestBody ContactFormRequest request) {
        try {
            // Basic validation
            if (request.getEmail() == null || request.getEmail().isEmpty() ||
                    request.getMessage() == null || request.getMessage().isEmpty() ||
                    request.getType() == null || request.getType().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("status", "error", "info", "Email, message, and type are required fields."));
            }

            // Extract all fields that might be present
            String fromEmail = request.getEmail();
            String messageBody = request.getMessage();
            String type = request.getType();
            String fromName = request.getName();
            String subjectFromForm = request.getSubject();
            String contactNumber = request.getNumber();

            // Call the email service with all relevant fields
            String result = emailService.sendSupportEmail(
                    fromEmail,
                    messageBody,
                    type,
                    fromName,
                    subjectFromForm,
                    contactNumber
            );

            boolean success = result.toLowerCase().contains("successfully");

            return ResponseEntity.ok(Map.of(
                    "status", success ? "sent" : "error",
                    "info", result
            ));
        } catch (Exception e) {
//            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "status", "error",
                    "info", "Exception: " + e.getMessage()
            ));
        }
    }
}