package com.suryapropertyconsultant.suryapropertyconsultant.Service; // Or your security package

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
import com.suryapropertyconsultant.suryapropertyconsultant.Repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminUserRepository adminUserRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // Changed parameter name to email for clarity
//        // Find the AdminUser by email
//        AdminUser adminUser = adminUserRepository.findByEmail(email) // Using findByEmail, correct
//                .orElseThrow(() -> new UsernameNotFoundException("AdminUser not found with email: " + email));
//
//        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + adminUser.getRole()));
//
//        return new User(
//                adminUser.getEmail(), // *** CRITICAL CHANGE: Use email here, consistent with lookup ***
//                adminUser.getPassword(),
////                authorities
//                Collections.emptyList()
//        );
//    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminUser user = adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }

}