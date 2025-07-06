package com.suryapropertyconsultant.suryapropertyconsultant.Service;

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
import com.suryapropertyconsultant.suryapropertyconsultant.Repository.AdminUserRepository;
import com.suryapropertyconsultant.suryapropertyconsultant.Utility.JwtUtil; // Import your JwtUtil
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Keep for registration
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AdminUserRepository adminUserRepository; // Inject repository
    private final JwtUtil jwtUtil; // Inject your JwtUtil

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, AdminUserRepository adminUserRepository, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.adminUserRepository = adminUserRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<AdminUser> getAllAdmins() {
        return adminUserRepository.findAll();
    }

    // --- Registration Logic ---
    // Ensure you use this method to save new admin users with encoded passwords
    public AdminUser register(AdminUser adminUser) {
        // Encode password before saving
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        adminUserRepository.save(adminUser); // Save to MongoDB
        return adminUser;
    }

    // --- JWT Generation (after successful authentication) ---
    // This method will be called by AuthController after AuthenticationManager authenticates
    public String generateToken(String email) {
        // Fetch user details to generate token with correct claims (e.g., roles)
        AdminUser user = adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return jwtUtil.generateToken(user); // make sure this method encodes properly
    }

    // --- JWT Validation ---
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    // --- Extract Username from Token ---
    public String extractUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }

    // --- Removed/Modified authenticate method ---
    // The previous manual 'authenticate' method is now obsolete as AuthenticationManager handles it.
    // If you need a method to just retrieve a user by email without password check, you can keep this
    // but rename it to something like 'findAdminUserByEmail'.
    public Optional<AdminUser> findAdminUserByEmail(String email) {
        return adminUserRepository.findByEmail(email);
    }
}