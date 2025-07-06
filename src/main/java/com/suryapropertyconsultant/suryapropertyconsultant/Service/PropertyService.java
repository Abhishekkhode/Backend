//package com.suryapropertyconsultant.suryapropertyconsultant.Service;
//
//import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Property;
//import com.suryapropertyconsultant.suryapropertyconsultant.Repository.PropertyRepo;
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PropertyService {
//    @Autowired
//    private PropertyRepo repository;
//
//    public List<Property> getAll() {
//        return repository.findAll();
//    }
//
//    public Property add(Property property) {
//        return repository.save(property);
//    }
//
//    public void delete(String id) {
//        repository.deleteById(id);
//    }
//
//    public Property update(String id, Property newData) {
//        Property existing = repository.findById(id).orElseThrow();
//        // set fields...
//        // 🏠 Basic property fields
//        existing.setTitle(newData.getTitle());
//        existing.setLocation(newData.getLocation());
//        existing.setPrice(newData.getPrice());
//        existing.setStatus(newData.getStatus());
//        existing.setBedrooms(newData.getBedrooms());
//        existing.setBathrooms(newData.getBathrooms());
//        existing.setArea(newData.getArea());
//        existing.setParking(newData.getParking());
//        existing.setDescription(newData.getDescription());
//        existing.setFeatures(newData.getFeatures());
//        existing.setYearBuilt(newData.getYearBuilt());
//        existing.setPossession(newData.getPossession());
//
//        // 🧑 Agent details
//        existing.setAgentName(newData.getAgentName());
//        existing.setAgentPhone(newData.getAgentPhone());
//        existing.setAgentEmail(newData.getAgentEmail());
//        existing.setAgentPhoto(newData.getAgentPhoto());
//
//        // 🖼️ Images
//        existing.setImages(newData.getImages());
//        return repository.save(existing);
//    }
////    findById
//    public Optional<Property> findById(String id){
//        return  repository.findById(id);
//    }
//}
//









//USING DTOS
package com.suryapropertyconsultant.suryapropertyconsultant.Service;

import com.suryapropertyconsultant.suryapropertyconsultant.Dto.PropertyRequestDTO;
import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Property;
import com.suryapropertyconsultant.suryapropertyconsultant.Repository.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepo propertyRepository;

    @Autowired
    public PropertyService(PropertyRepo propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // Helper method to convert DTO to Entity
    private Property convertDtoToEntity(PropertyRequestDTO dto) {
        Property property = new Property();
        property.setTitle(dto.getTitle());
        property.setPrice(dto.getPrice());
        property.setLocation(dto.getLocation());
        property.setBedrooms(dto.getBedrooms());
        property.setBathrooms(dto.getBathrooms());
        property.setArea(dto.getArea());
        property.setType(dto.getType());
        property.setStatus(dto.getStatus());
        property.setDescription(dto.getDescription());
        property.setFeatures(dto.getFeatures());
        property.setYearBuilt(dto.getYearBuilt());
        property.setParking(dto.getParking());
        property.setFeatured(dto.isFeatured());
        property.setImages(dto.getImages());
        property.setPossession(dto.getPossession());

        // Set agentId (now optional via DTO validation)
//        property.setAgentId(dto.getAgentId());

        return property;
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getPropertyById(String id) {
        return propertyRepository.findById(id);
    }

    public Property createProperty(PropertyRequestDTO propertyDto) {
        Property property = convertDtoToEntity(propertyDto);
        return propertyRepository.save(property);
    }

    public Property updateProperty(String id, PropertyRequestDTO propertyDto) {
        Optional<Property> existingPropertyOptional = propertyRepository.findById(id);
        if (existingPropertyOptional.isPresent()) {
            Property existingProperty = existingPropertyOptional.get();

            existingProperty.setTitle(propertyDto.getTitle());
            existingProperty.setPrice(propertyDto.getPrice());
            existingProperty.setLocation(propertyDto.getLocation());
            existingProperty.setBedrooms(propertyDto.getBedrooms());
            existingProperty.setBathrooms(propertyDto.getBathrooms());
            existingProperty.setArea(propertyDto.getArea());
            existingProperty.setType(propertyDto.getType());
            existingProperty.setStatus(propertyDto.getStatus());
            existingProperty.setDescription(propertyDto.getDescription());
            existingProperty.setFeatures(propertyDto.getFeatures());
            existingProperty.setYearBuilt(propertyDto.getYearBuilt());
            existingProperty.setParking(propertyDto.getParking());
            existingProperty.setFeatured(propertyDto.isFeatured());
            existingProperty.setImages(propertyDto.getImages());
            existingProperty.setPossession(propertyDto.getPossession());
            return propertyRepository.save(existingProperty);
        } else {
            throw new RuntimeException("Property with ID " + id + " not found.");
        }
    }

    public void deleteProperty(String id) {
        propertyRepository.deleteById(id);
    }
}