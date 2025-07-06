//package com.suryapropertyconsultant.suryapropertyconsultant.Repository;
// src/main/java/com/suryapropertyconsultant/suryapropertyconsultant/Repository/AgentRepository.java
package com.suryapropertyconsultant.suryapropertyconsultant.Repository;

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Agent;
//import org.springframework.data.jpa.repository.JpaRepository; // For JPA
// OR import org.springframework.data.mongodb.repository.MongoRepository; // For MongoDB
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
// For JPA
public interface AgentRepository extends MongoRepository<Agent, String> { }

// For MongoDB
/*
public interface AgentRepository extends MongoRepository<Agent, String> { }
*/
