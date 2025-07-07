//package com.suryapropertyconsultant.suryapropertyconsultant.Controller;
//
//import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
//import com.suryapropertyconsultant.suryapropertyconsultant.Dto.AdminUserDTO;
//import com.suryapropertyconsultant.suryapropertyconsultant.Entity.Property;
//import com.suryapropertyconsultant.suryapropertyconsultant.Repository.AdminUserRepository;
//import com.suryapropertyconsultant.suryapropertyconsultant.Repository.PropertyRepo;
//import com.suryapropertyconsultant.suryapropertyconsultant.Service.AdminService;
//import com.suryapropertyconsultant.suryapropertyconsultant.Service.AuthService;
//import com.suryapropertyconsultant.suryapropertyconsultant.Service.CustomUserDetails;
//import com.suryapropertyconsultant.suryapropertyconsultant.Service.PropertyService;
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException; // Import this
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.security.access.prepost.PreAuthorize;
//
//import java.sql.SQLOutput;
//import java.util.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:3000")
//public class AuthController {
//
//    @Autowired
//    private AdminUserRepository adminUserRepository;
//
//    @Autowired
//    private AuthService authService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private PropertyService service;
//
//    @Autowired
//    private AdminService adminService;
////    @Autowired
////    private PropertyRepo propertyRepo;
//
//
//    @GetMapping
//    public ResponseEntity<?> getAll() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//
//        Optional<AdminUser> user = adminService.findByUserName(email);
//
//        if (user.isPresent()) {
//            System.out.println("GET: Fetching all properties");
//            return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
//        }
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//    }
//
//    @GetMapping("/admin")
//    public ResponseEntity<?> getAdmins(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//
//        Optional<AdminUser> user = adminService.findByUserName(email);
//        if(user.isPresent()){
//            System.out.println("Getting All Admins : ");
//            return new ResponseEntity<>(adminService.getall(),HttpStatus.OK);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
////        List<AdminUser> adminUsers = adminService.getall();
////        return new ResponseEntity<>(adminUsers, HttpStatus.OK);
//    }
//
//
//    // --- Create Admin Endpoint ---
//    @PostMapping
//    public ResponseEntity<?> createAdmin(@RequestBody AdminUser adminUser) {
//        // Ensure role is set to ADMIN for new admin users
//        adminUser.setRole("ADMIN");
//        authService.register(adminUser); // Assuming authService.register handles password encoding
//        return ResponseEntity.status(HttpStatus.CREATED).body("Admin Created successfully.");
//    }
//
//
//        @PostMapping("/login")
//        public ResponseEntity<?> login(@RequestBody AdminUser loginRequest) {
//            try {
//                Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                loginRequest.getEmail(), loginRequest.getPassword())
//                );
//    //
//    //            AdminUser userDetails = (AdminUser) authentication.getPrincipal();
//    //            return ResponseEntity.ok("Login successful for user: " + userDetails.getEmail());
//                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//                AdminUser user = userDetails.getUser();
//                String token = authService.generateToken(user.getEmail());
//
//                Map<String, String> response = new HashMap<>();
//                response.put("token", token);
//    //            response.put("admin", new AdminUserDTO(user));
//                response.put("message", "Login successful");
//                // Optionally, you might want to return some basic user details like role, email, etc.
//                response.put("email", user.getEmail());
//
//                return ResponseEntity.ok(response);
//
//            } catch (Exception e) {
//                Map<String, String> error = new HashMap<>();
//                error.put("message", "Invalid credentials.........");
//                return ResponseEntity.status(401).body(error);
////                return ResponseEntity.status(401).body("Invalid credentials"+e.getMessage());
//            }
//        }
//
//    // --- Login Endpoint ---ORGINAL END POINT
//    @PostMapping("/demologin")
//    public ResponseEntity<?> log(@RequestBody AdminUser adminUser) {
//        String email = adminUser.getEmail();
//        String password = adminUser.getPassword();
//
//        try {
//            // Step 1: Authenticate credentials using Spring Security's AuthenticationManager
//            // This will internally use your AdminUserDetailsService and PasswordEncoder
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(email, password)
//            );
//
//            // If authentication is successful, generate JWT token
//            String token = authService.generateToken(email);
//
//            // Return success response with the token
//            Map<String, String> response = new HashMap<>();
//            response.put("token", token);
//            response.put("message", "Login successful");
//            // Optionally, you might want to return some basic user details like role, email, etc.
//            response.put("email", email); // Example: include email in response
//            // If you have a fully populated AdminUser object after authentication, you could return it:
//            // AdminUser authenticatedAdmin = (AdminUser) authentication.getPrincipal(); // if AdminUser implements UserDetails
//            // response.put("adminUser", authenticatedAdmin); // This might require DTO for security/serialization
//
//            return ResponseEntity.ok(response);
//
//        } catch (UsernameNotFoundException e) {
//            Map<String, Object> error = new HashMap<>();
//            error.put("error", "UsernameNotFoundException: " + e.getMessage());
//            // Specific exception for user not found by AdminUserDetailsService
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
//        } catch (BadCredentialsException e) {
//            Map<String, Object> error = new HashMap<>();
//            error.put("error", "BadCredentialsException: " + e.getMessage());
//            // Specific exception for incorrect password
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
//        } catch (AuthenticationException e) {
//            Map<String, Object> error = new HashMap<>();
//            error.put("error", "AuthenticationException: " + e.getMessage());
//            // General authentication failure (e.g., account disabled, locked etc.)
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage()+error);
//        } catch (Exception e) {
//            Map<String, Object> error = new HashMap<>();
//            error.put("error", "Exception: " + e.getMessage());
//            // Catch any other unexpected exceptions
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login: " +error+ e.getMessage());
//        }
//    }
//
//    // --- Token Validation Endpoint ---
//    @GetMapping("/validate")
//    public ResponseEntity<?> validateToken(@RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            String token = authorizationHeader.substring(7); // Extract token (remove "Bearer ")
//            try {
//                // Validate the token and extract username (email in your case)
//                if (authService.validateToken(token)) {
//                    String userEmail = authService.extractUsernameFromToken(token); // Get username (email) from token
//                    Map<String, String> response = new HashMap<>();
//                    response.put("message", "Token is valid");
//                    response.put("email", userEmail);
//                    return ResponseEntity.ok(response);
//                }
//            } catch (Exception e) {
//                // Token validation failed (e.g., expired, invalid signature)
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
//            }
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Token missing or invalid format.");
//    }
//
////        @PutMapping("{id}")
////        public ResponseEntity<Property> updatePropertyDemo(@PathVariable String id, @RequestBody Property property) {
////            return ResponseEntity.ok(service.update(id, property));
////        }
//
////    @PutMapping("/properties/{id}")
////    public ResponseEntity<Property> updateProperty(@PathVariable String id, @RequestBody Property updatedProperty) {
////        return service.findById(id)
////                .map(property -> {
////                    updatedProperty.setId(property.getId());
////                    return ResponseEntity.ok(service.update(id, updatedProperty));
////                })
////                .orElse(ResponseEntity.notFound().build());
////    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/dashboard/{id}")
//    public ResponseEntity<?> deletemaping(@PathVariable String id){
//        Optional<Property> byId = service.findById(id);
//        if(byId.isPresent()) {
//            service.delete(id);
//        }else{
//            return new ResponseEntity<>("No Property With the ID: " + id, HttpStatus.BAD_REQUEST);
//        }
////        return new ResponseEntity<>(HttpStatus.OK).getBody("Deleted Property by Id + :" +id);
//        return new ResponseEntity<>("Deleted Property by Id: " + id, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/properties/{id}")
//    public Property update(@PathVariable String id, @RequestBody Property property) {
//        System.out.println("PUT: Updating property with id = " + id);
//        return service.update(id, property);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/property")
//    public Property add(@RequestBody Property property) {
//        return service.add(property);
//    }
//}















/// src/main/java/com/suryapropertyconsultant/suryapropertyconsultant/Controller/AuthController.java
package com.suryapropertyconsultant.suryapropertyconsultant.Controller;

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
import com.suryapropertyconsultant.suryapropertyconsultant.Dto.AdminUserDTO;
import com.suryapropertyconsultant.suryapropertyconsultant.Service.AdminService;
import com.suryapropertyconsultant.suryapropertyconsultant.Service.AuthService;
import com.suryapropertyconsultant.suryapropertyconsultant.Service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth") // Base path for authentication
@CrossOrigin(origins = "http://localhost:3000") // Keep CORS for frontend
public class AuthController {

    // Removed AdminUserRepository as it's typically accessed via AdminService or AuthService
    // @Autowired private AdminUserRepository adminUserRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Removed PropertyService and PropertyRepo as they are now in PropertyController
    // @Autowired private PropertyService service;

    @Autowired
    private AdminService adminService;


    /**
     * Retrieves all admin users.
     * Requires ADMIN role.
     * @return A ResponseEntity containing a list of AdminUser objects and HTTP status OK.
     */
    @PreAuthorize("hasRole('ADMIN')") // Added PreAuthorize for this admin-specific endpoint
    @GetMapping("/admin")
    public ResponseEntity<?> getAdmins() {
        // Authenticate the current user to ensure they have the ADMIN role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<AdminUser> user = adminService.findByUserName(email);
        if (user.isPresent()) {
            System.out.println("Getting All Admins : ");
            List<AdminUserDTO> dtos = adminService.getall()
                    .stream()
                    .map(AdminUserDTO::new)
                    .toList();
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }
        // This line should ideally not be reached if @PreAuthorize works correctly,
        // but it's a fallback for clarity.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }


    /**
     * Creates a new admin user.
     * This endpoint should ideally be secured or only accessible by existing admins.
     * For now, it's public, but consider adding @PreAuthorize("hasRole('ADMIN')")
     * or a separate initial setup process for production.
     * @param adminUser The AdminUser object containing registration details.
     * @return A ResponseEntity indicating success or failure.
     */
    // For production, consider adding @PreAuthorize("hasRole('ADMIN')")
    // or making this part of a secure initial setup process.
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register") // Changed mapping to be more explicit for registration
    public ResponseEntity<?> createAdmin(@RequestBody AdminUserDTO adminUser) {
        // Ensure role is set to ADMIN for new admin users
        // This logic should ideally be handled in the service layer for consistency
        try{
        AdminUser user = new AdminUser();
        user.setEmail(adminUser.getEmail());
        user.setPassword(adminUser.getPassword());
        user.setRole("ADMIN");
        AdminUser createdUser = authService.register(user);// Assuming authService.register handles password encoding
        AdminUserDTO responseDTO = new AdminUserDTO(createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin Created successfully.");
        } catch (Exception e) {
            // Handle specific exceptions like duplicate email if your service throws them
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating admin: " + e.getMessage());
        }
    }


    /**
     * Handles user login and generates a JWT token upon successful authentication.
     * @param loginRequest AdminUser object containing email and password.
     * @return A ResponseEntity with the JWT token and success message, or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminUserDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            AdminUser user = userDetails.getUser();
            String token = authService.generateToken(user.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login successful");
            response.put("email", user.getEmail());
            // If you have an AdminUserDTO, you could return it here instead of just email
            // response.put("user", new AdminUserDTO(user));

            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            // Specific exceptions for login failures
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (AuthenticationException e) {
            // General authentication failure (e.g., account disabled, locked etc.)
            Map<String, String> error = new HashMap<>();
            error.put("message", "Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            Map<String, String> error = new HashMap<>();
            error.put("message", "An unexpected error occurred during login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Validates a JWT token provided in the Authorization header.
     * @param authorizationHeader The Authorization header containing the Bearer token.
     * @return A ResponseEntity indicating token validity and user email, or an error message.
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extract token (remove "Bearer ")
            try {
                if (authService.validateToken(token)) {
                    String userEmail = authService.extractUsernameFromToken(token); // Get username (email) from token
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Token is valid");
                    response.put("email", userEmail);
                    return ResponseEntity.ok(response);
                }
            } catch (Exception e) {
                // Token validation failed (e.g., expired, invalid signature)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Token missing or invalid format.");
    }
}