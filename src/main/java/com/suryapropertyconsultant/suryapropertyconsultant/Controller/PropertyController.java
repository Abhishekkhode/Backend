package com.suryapropertyconsultant.suryapropertyconsultant.Controller;

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Property;
import com.suryapropertyconsultant.suryapropertyconsultant.Service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/properties") // allow React to access this API
public class PropertyController {

    @Autowired
    private PropertyService service;

    @GetMapping
    public List<Property> getAll() {
        System.out.println("GET: Fetching all properties");
        return service.getAll();
    }



//    @PutMapping("/{id}")
//    public Property update(@PathVariable String id, @RequestBody Property property) {
//        return service.update(id, property);
//    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

//    @PutMapping("/properties/{id}")
//    public ResponseEntity<Property> updateProperty(@PathVariable String id, @RequestBody Property updatedProperty) {
//        return service.findById(id)
//                .map(property -> {
//                    updatedProperty.setId(property.getId());
//                    return ResponseEntity.ok(service.update(id, updatedProperty));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
}

