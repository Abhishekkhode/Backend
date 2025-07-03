package com.suryapropertyconsultant.suryapropertyconsultant.Service;

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
import com.suryapropertyconsultant.suryapropertyconsultant.Repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    AdminUserRepository adminUserRepository;

    public Optional<AdminUser> findByUserName(String email) {
        return adminUserRepository.findByEmail(email);
    }
    public List<AdminUser> getall(){
        return adminUserRepository.findAll();
    }

//    public final
}
