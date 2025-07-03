package com.suryapropertyconsultant.suryapropertyconsultant.Repository;

//import com.example.demo.model.AdminUser
import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminUserRepository extends MongoRepository<AdminUser, String> {
//    Optional<AdminUser> findByUsername(String username);
    Optional<AdminUser> findByEmail(String email);
//    AdminUser findByUsername(String username);
}