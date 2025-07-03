package com.suryapropertyconsultant.suryapropertyconsultant.Repository;

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepo extends MongoRepository<Property, String> {
}
