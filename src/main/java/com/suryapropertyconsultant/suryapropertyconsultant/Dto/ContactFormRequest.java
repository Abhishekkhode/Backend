package com.suryapropertyconsultant.suryapropertyconsultant.Dto;

import lombok.Data;

@Data
public class ContactFormRequest {
    // Common fields
    private String email;
    private String message;
    private String type; // To distinguish: "admin_support" or "general_inquiry"

    // Fields specific to General Contact form
    private String name;
    private String number; // Use String for phone numbers to retain formatting like spaces or hyphens
    private String subject; // This will hold the selected subject from the dropdown
}