package com.suryapropertyconsultant.suryapropertyconsultant.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data // Generates getters, setters, equals, hashCode, toString
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
@Document(collection = "properties") // For MongoDB
public class Property {

    @Id // Use @Id for MongoDB document ID
    private String id; // MongoDB typically uses String for IDs

    private String title;
    private Double price;
    private String location;
    private int bedrooms;
    private int bathrooms;
    private Double area;
    private String type;
    private String status;
    private String description;

    private List<String> features;
    private Integer yearBuilt;
    private Integer parking;
    private boolean featured;
    private List<String> images;
    private String possession;
}