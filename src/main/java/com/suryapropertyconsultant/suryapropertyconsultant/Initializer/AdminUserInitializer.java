package com.suryapropertyconsultant.suryapropertyconsultant.Initializer;// Example: src/main/java/com/suryapropertyconsultant/suryapropertyconsultant/YourInitializerClass.java
// Or this could be a method inside your main SuryapropertyconsultantApplication class
// Or anywhere you initialize the admin user

//package com.suryapropertyconsultant.suryapropertyconsultant.YourInitializerClass; // Adjust package

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
import com.suryapropertyconsultant.suryapropertyconsultant.Repository.AdminUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
//import org.h2.security.SHA256;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "user";

        // CHECK IF USER ALREADY EXISTS BEFORE CREATING
        if (adminUserRepository.findByEmail(adminEmail).isEmpty()) {
            AdminUser adminUser = new AdminUser(
                    adminEmail,
                    passwordEncoder.encode("user"), // Use your actual admin password
                    "USER1", // Your desired username for the admin
                    "ADMIN"
            );
            adminUserRepository.save(adminUser);
            System.out.println("Admin user created successfully!");
        } else {
            System.out.println("Admin user already exists. Skipping creation.");
        }
    }
}