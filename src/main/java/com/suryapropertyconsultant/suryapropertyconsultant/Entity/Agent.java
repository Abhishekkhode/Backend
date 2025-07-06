package com.suryapropertyconsultant.suryapropertyconsultant.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data // Generates getters, setters, equals, hashCode, toString
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Document(collection = "Agents") // For MongoDB
public class Agent {

    @Id // Use @Id for MongoDB document ID
    private String id; // MongoDB typically uses String for IDs

    private String agentName;
    private String agentPhone;
    private String agentEmail;
    private String agentPhoto; // Optional
    @JsonProperty("Tgreranumber")
    private String Tgreranumber;
}