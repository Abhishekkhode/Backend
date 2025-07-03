package com.suryapropertyconsultant.suryapropertyconsultant.Service;

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Property;
import com.suryapropertyconsultant.suryapropertyconsultant.Repository.PropertyRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepo repository;

    public List<Property> getAll() {
        return repository.findAll();
    }

    public Property add(Property property) {
        return repository.save(property);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public Property update(String id, Property newData) {
        Property existing = repository.findById(id).orElseThrow();
        // set fields...
        // 🏠 Basic property fields
        existing.setTitle(newData.getTitle());
        existing.setLocation(newData.getLocation());
        existing.setPrice(newData.getPrice());
        existing.setStatus(newData.getStatus());
        existing.setBedrooms(newData.getBedrooms());
        existing.setBathrooms(newData.getBathrooms());
        existing.setArea(newData.getArea());
        existing.setParking(newData.getParking());
        existing.setDescription(newData.getDescription());
        existing.setFeatures(newData.getFeatures());
        existing.setYearBuilt(newData.getYearBuilt());
        existing.setPossession(newData.getPossession());

        // 🧑 Agent details
        existing.setAgentName(newData.getAgentName());
        existing.setAgentPhone(newData.getAgentPhone());
        existing.setAgentEmail(newData.getAgentEmail());
        existing.setAgentPhoto(newData.getAgentPhoto());

        // 🖼️ Images
        existing.setImages(newData.getImages());
        return repository.save(existing);
    }
//    findById
    public Optional<Property> findById(String id){
        return  repository.findById(id);
    }
}

