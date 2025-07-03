package com.suryapropertyconsultant.suryapropertyconsultant.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "properties")
@Data
public class Property {

    @Id
    private String id; // MongoDB uses String _id by default

    private String title;
    private String location;
    private Double price;
    private String status; // for-sale, for-rent, sold
    private int bedrooms;
    private int bathrooms;
    private int area;
    private int parking;
    private String description;
    private List<String> features; // a list of strings instead of comma-separated
    private int yearBuilt;
    private String possession;

    private String agentName;
    private String agentPhone;
    private String agentEmail;
    private String agentPhoto;

    private List<String> images;
}
