package com.suryapropertyconsultant.suryapropertyconsultant.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, equals, hashCode, toString
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
public class AgentRequestDTO {

    @NotBlank(message = "Agent name cannot be blank")
    @Size(min = 3, max = 100, message = "Agent name must be between 3 and 100 characters")
    private String agentName;

    @NotBlank(message = "Agent phone number cannot be blank")
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Phone number must be valid")
    private String agentPhone;

    @NotBlank(message = "Agent email cannot be blank")
    @Email(message = "Agent email must be valid")
    private String agentEmail;

    private String agentPhoto; // Optional

    @JsonProperty("Tgreranumber")
    @NotBlank(message = "Agent TGRERA Number cannot be blank")
    private String Tgreranumber;
}