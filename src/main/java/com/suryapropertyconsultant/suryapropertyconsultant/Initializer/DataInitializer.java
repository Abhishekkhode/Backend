//    // src/main/java/com/suryapropertyconsultant/suryapropertyconsultant/Initializer/DataInitializer.java (create this file)
//
//    package com.suryapropertyconsultant.suryapropertyconsultant.Initializer;
//
//    import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
//    import com.suryapropertyconsultant.suryapropertyconsultant.Repository.AdminUserRepository;
//    import org.springframework.boot.CommandLineRunner;
//    import org.springframework.security.crypto.password.PasswordEncoder; // Inject this
//    import org.springframework.stereotype.Component;
//
//    @Component
//    public class DataInitializer implements CommandLineRunner {
//
//        private final AdminUserRepository adminUserRepository;
//        private final PasswordEncoder passwordEncoder; // Autowire PasswordEncoder
//
//        public DataInitializer(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
//            this.adminUserRepository = adminUserRepository;
//            this.passwordEncoder = passwordEncoder;
//        }
//
//        @Override
//        public void run(String... args) throws Exception {
//            // This will only run if an admin user with this specific username (email) doesn't already exist
//            // This prevents creating duplicate users on every app restart.
//            if (adminUserRepository.findByEmail("user").isEmpty()) { // <<< Use your desired admin EMAIL as the username
//                AdminUser admin = new AdminUser();
//    //            admin.setUsername("admin@example.com"); // <<< Set the actual ADMIN EMAIL here (this is your username in DB)
//                // Hash the password before saving! Use a strong password!
//                admin.setPassword(passwordEncoder.encode("user")); // <<< Set a strong, secure password here
//                admin.setRole("ADMIN"); // Ensure the role is "ADMIN"
//
//                adminUserRepository.save(admin);
//                System.out.println("Initial admin user created: " + admin.getEmail());
//            } else {
//                System.out.println("Admin user (admin@example.com) already exists. Skipping initial creation.");
//            }
//        }
//    }
package com.suryapropertyconsultant.suryapropertyconsultant.Initializer;

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
import com.suryapropertyconsultant.suryapropertyconsultant.Repository.AdminUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Initializes a default admin user in the database if one does not already exist.
 * This class runs automatically when the Spring Boot application starts.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for DataInitializer, injecting the necessary repositories and encoder.
     * @param adminUserRepository The repository for AdminUser entities.
     * @param passwordEncoder The password encoder for hashing passwords.
     */
    public DataInitializer(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method is executed automatically after the Spring application context has loaded.
     * It checks for the existence of a default admin user and creates one if not found.
     *
     * @param args Command line arguments (not used here).
     * @throws Exception If an error occurs during user creation.
     */
    @Override
    public void run(String... args) throws Exception {
        // Define the default admin's email and password.
        // It's highly recommended to use environment variables or a configuration server
        // for production passwords, not hardcoding them.
        String defaultAdminEmail = "admin@example.com"; // Choose a clear default admin email
        String defaultAdminPassword = "your_secure_password"; // Set a strong, secure password here

        // Check if an admin user with the default email already exists in the database.
        // This prevents creating duplicate users on every application restart.
        if (adminUserRepository.findByEmail(defaultAdminEmail).isEmpty()) {
            AdminUser admin = new AdminUser();
            admin.setEmail(defaultAdminEmail); // Set the email for the new admin user
            // Hash the password using the configured PasswordEncoder before saving it.
            admin.setPassword(passwordEncoder.encode(defaultAdminPassword));
            admin.setRole("ADMIN"); // Assign the "ADMIN" role to this user

            adminUserRepository.save(admin); // Save the new admin user to the database
            System.out.println("Initial admin user created: " + defaultAdminEmail);
        } else {
            System.out.println("Admin user (" + defaultAdminEmail + ") already exists. Skipping initial creation.");
        }
    }
}
