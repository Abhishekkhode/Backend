package com.suryapropertyconsultant.suryapropertyconsultant.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${support.mail.to}")
    private String supportMailTo;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public String sendSupportEmail(String userEmail, String userMessage) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(supportMailTo);
            message.setSubject("Support Request from " + userEmail);
            message.setText("Email: " + userEmail + "\n\nMessage:\n" + userMessage);

            mailSender.send(message);
            return "Support email sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send support email";
        }
    }
}
//package com.suryapropertyconsultant.suryapropertyconsultant.Service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.*;
//
//@Service
//public class EmailService {
//
//    @Value("${mailjet.api.key}")
//    private String apiKey;
//
//    @Value("${mailjet.secret.key}")
//    private String secretKey;
//
//    @Value("${mailjet.sender.email}")
//    private String senderEmail;
//
//    @Value("${mailjet.receiver.email}")
//    private String receiverEmail;
//
//    private static final String MAILJET_API_URL = "https://api.mailjet.com/v3.1/send";
//
//    public String sendSupportEmail(String userEmail, String userMessage) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String, Object> message = new HashMap<>();
//        message.put("From", Map.of(
//                "Email", senderEmail,
//                "Name", "Surya Property"
//        ));
//        message.put("To", List.of(Map.of(
//                "Email", receiverEmail,
//                "Name", "Support Team"
//        )));
//        message.put("Subject", "Message from " + userEmail);
//        message.put("HTMLPart", "<p><strong>Email:</strong> " + userEmail + "</p>" +
//                "<p><strong>Message:</strong><br>" + userMessage + "</p>");
//
//        Map<String, Object> requestBody = Map.of("Messages", List.of(message));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(apiKey, secretKey);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.postForEntity(MAILJET_API_URL, requestEntity, String.class);
//            if (response.getStatusCode().is2xxSuccessful()) {
//                return "Success: Email sent via Mailjet.";
//            } else {
//                return "Error: Mailjet responded with status " + response.getStatusCode();
//            }
//        } catch (Exception e) {
//            return "Exception while sending email: " + e.getMessage();
//        }
//    }
//}
