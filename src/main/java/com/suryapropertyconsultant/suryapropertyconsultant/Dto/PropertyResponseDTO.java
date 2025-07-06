//package com.suryapropertyconsultant.suryapropertyconsultant.Dto;
//
//public class PropertyResponseDTO {
//}
// src/main/java/com/suryapropertyconsultant/suryapropertyconsultant/dto/PropertyResponseDTO.java
package com.suryapropertyconsultant.suryapropertyconsultant.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data // Lombok annotation for getters, setters, equals, hashCode, toString
@NoArgsConstructor // Lombok annotation for a no-argument constructor
@AllArgsConstructor // Lombok annotation for an all-argument constructor
public class PropertyResponseDTO {

    private String id; // MongoDB generated ID, included in response

    private String title;
    private String location;
    private Double price;
    private String status;
    private int bedrooms;
    private int bathrooms;
    private Double area;
    private int parking;
    private String description;
    private List<String> features;
    private int yearBuilt;
    private String possession;
    private List<String> images;

    // You could add formatted fields here if needed, e.g.:
    // private String formattedPrice;
    // private String formattedArea;
    // private String lastUpdatedDate;
}
