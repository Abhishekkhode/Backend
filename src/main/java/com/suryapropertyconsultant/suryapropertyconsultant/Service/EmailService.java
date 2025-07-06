
//Orginal Working Email Service
//package com.suryapropertyconsultant.suryapropertyconsultant.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Value("${support.mail.to}")
//    private String supportMailTo;
//
//    @Value("${spring.mail.username}")
//    private String fromEmail;
//
//    public String sendSupportEmail(String userEmail, String userMessage) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom(fromEmail);
//            message.setTo(supportMailTo);
//            message.setSubject("Support Request from " + userEmail);
//            message.setText("Email: " + userEmail + "\n\nMessage:\n" + userMessage);
//
//            mailSender.send(message);
//            return "Support email sent successfully";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Failed to send support email";
//        }
//    }
//}















package com.suryapropertyconsultant.suryapropertyconsultant.Service;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final MailjetClient mailjetClient;

    @Value("${mailjet.sender.email}")
    private String senderEmail;

    @Value("${mailjet.sender.name}")
    private String senderName;

    @Value("${support.email.recipient}")
    private String recipientEmail;

    // Constructor to initialize MailjetClient with API keys from application.properties
    public EmailService(@Value("${mailjet.api.key}") String apiKey,
                        @Value("${mailjet.secret.key}") String secretKey) {
        this.mailjetClient = new MailjetClient(ClientOptions.builder()
                .apiKey(apiKey)
                .apiSecretKey(secretKey)
                .build());
    }

    public String sendSupportEmail(String fromEmail, String messageBody, String type, String fromName, String subjectFromForm, String contactNumber) {
        String emailSubject;
        String htmlContent;

        // --- Customize email based on 'type' and frontend inputs ---
        switch (type) {
            case "admin_support":
                emailSubject = "Admin Details Recovery"; // Subject for admin page
                htmlContent = "<html><body>"
                        + "<h3>New Admin Support Request:</h3>"
                        + "<p><strong>Requester Email:</strong> " + fromEmail + "</p>"
                        + "<p><strong>Message:</strong></p>"
                        + "<p>" + messageBody + "</p>"
                        + "<br><p>This request is for Admin Login credentials recovery.</p>"
                        + "</body></html>";
                break;

            case "general_inquiry":
                String displayFromName = (fromName != null && !fromName.isEmpty()) ? fromName : "Website Visitor";
                emailSubject = "Contact: " + (subjectFromForm != null && !subjectFromForm.isEmpty() ? subjectFromForm : "General Inquiry"); // Subject from form or default

                htmlContent = "<html><body>"
                        + "<h3>New General Inquiry from Website:</h3>"
                        + "<p><strong>Name:</strong> " + displayFromName + "</p>"
                        + "<p><strong>Email:</strong> " + fromEmail + "</p>";
                if (contactNumber != null && !contactNumber.isEmpty()) {
                    htmlContent += "<p><strong>Phone Number:</strong> " + contactNumber + "</p>";
                }
                if (subjectFromForm != null && !subjectFromForm.isEmpty()) {
                    htmlContent += "<p><strong>Subject:</strong> " + subjectFromForm + "</p>";
                }
                htmlContent += "<p><strong>Message:</strong></p>"
                        + "<p>" + messageBody + "</p>"
                        + "<br><p>Reply to: " + fromEmail + "</p>" // Indicate original sender for reply
                        + "</body></html>";
                break;

            default:
                // Fallback for unexpected types
                emailSubject = "New Contact Form Submission (Unknown Type)";
                htmlContent = "<html><body>"
                        + "<h3>New Contact Form Submission (Unknown Type):</h3>"
                        + "<p><strong>From Email:</strong> " + fromEmail + "</p>"
                        + "<p><strong>Message:</strong></p>"
                        + "<p>" + messageBody + "</p>"
                        + "<br><p>This submission did not specify a recognized type.</p>"
                        + "<br><p>Reply to: " + fromEmail + "</p>"
                        + "</body></html>";
                break;
        }

        try {
            MailjetRequest request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", senderEmail)
                                            .put("Name", senderName))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", recipientEmail)
                                                    .put("Name", "Support Recipient"))) // Name for recipient
                                    .put(Emailv31.Message.SUBJECT, emailSubject)
                                    .put(Emailv31.Message.HTMLPART, htmlContent)
                                    .put(Emailv31.Message.CUSTOMID, type + "_Form_Submission")
                                    // Set Reply-To to the user's email so you can reply directly
                                    .put(Emailv31.Message.HEADERS, new JSONObject()
                                            .put("Reply-To", fromEmail))));


            MailjetResponse response = mailjetClient.post(request);

            if (response.getStatus() == 200) {
                System.out.println("Mailjet Email Sent Successfully. Status: " + response.getStatus());
                System.out.println("Response Data: " + response.getData());
                return "Email sent successfully via Mailjet.";
            } else {
                System.err.println("Mailjet Email Failed. Status: " + response.getStatus());
                System.err.println("Response Data: " + response.getData());
                return "Failed to send email via Mailjet: " + response.getData().toString();
            }

        } catch (Exception e) {
            System.err.println("Exception while sending email via Mailjet: " + e.getMessage());
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }
}

















//Using MailJet
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
