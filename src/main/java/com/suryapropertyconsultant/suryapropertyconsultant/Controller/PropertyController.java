//package com.suryapropertyconsultant.suryapropertyconsultant.Controller;
//
//import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Property;
//import com.suryapropertyconsultant.suryapropertyconsultant.Service.PropertyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//@RequestMapping("/api/properties") // allow React to access this API
//public class PropertyController {
//
//    @Autowired
//    private PropertyService service;
//
//    @GetMapping
//    public List<Property> getAll() {
//        System.out.println("GET: Fetching all properties");
//        return service.getAll();
//    }
//
//
//
////    @PutMapping("/{id}")
////    public Property update(@PathVariable String id, @RequestBody Property property) {
////        return service.update(id, property);
////    }
//
//
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable String id) {
//        service.delete(id);
//    }
//
////    @PutMapping("/properties/{id}")
////    public ResponseEntity<Property> updateProperty(@PathVariable String id, @RequestBody Property updatedProperty) {
////        return service.findById(id)
////                .map(property -> {
////                    updatedProperty.setId(property.getId());
////                    return ResponseEntity.ok(service.update(id, updatedProperty));
////                })
////                .orElse(ResponseEntity.notFound().build());
////    }
//}
//








package com.suryapropertyconsultant.suryapropertyconsultant.Controller;

import com.suryapropertyconsultant.suryapropertyconsultant.Dto.PropertyRequestDTO;
import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Property;
import com.suryapropertyconsultant.suryapropertyconsultant.Service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable String id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        return property.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Property> createProperty(@Valid @RequestBody PropertyRequestDTO propertyDto) {
        Property createdProperty = propertyService.createProperty(propertyDto);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable String id, @Valid @RequestBody PropertyRequestDTO propertyDto) {
        try {
            Property updatedProperty = propertyService.updateProperty(id, propertyDto);
            return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String id) {
        try {
            propertyService.deleteProperty(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}