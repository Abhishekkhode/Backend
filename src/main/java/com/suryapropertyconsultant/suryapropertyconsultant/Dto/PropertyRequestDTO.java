package com.suryapropertyconsultant.suryapropertyconsultant.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Generates getters, setters, equals, hashCode, toString
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
public class PropertyRequestDTO {

    @NotBlank(message = "Title cannot be blank")
//    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotNull(message = "Price cannot be null")
//    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    @Min(value = 0, message = "Bedrooms cannot be negative")
    private int bedrooms;

    @Min(value = 0, message = "Bathrooms cannot be negative")
    private int bathrooms;

    @Min(value = 0, message = "Area cannot be negative")
    private Double area;

    @NotBlank(message = "Property type cannot be blank")
    private String type;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotBlank(message = "Description cannot be blank")
//    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;

    private List<String> features;

//    @Min(value = 1800, message = "Year built must be after 1800")
//    @Max(value = 2100, message = "Year built cannot be in the far future")
    private Integer yearBuilt;

    @Min(value = 0, message = "Parking spaces cannot be negative")
    private Integer parking;

    private boolean featured;

//    @NotEmpty(message = "At least one image URL is required")
    private List<String> images;

    @NotBlank(message = "Possession status cannot be blank")
//    @Size(min = 3, max = 50, message = "Possession status must be between 3 and 50 characters")
    private String possession;

}