package com.suryapropertyconsultant.suryapropertyconsultant.Configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
//public class Config {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**").allowedOrigins("http://localhost:3000");
//            }
//        };
//    }
//
//}
// src/main/java/com/suryapropertyconsultant/suryapropertyconsultant/Config.java (or config package)

//package com.suryapropertyconsultant.suryapropertyconsultant; // Adjust package to yours

import com.suryapropertyconsultant.suryapropertyconsultant.Service.AdminUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService; // Make sure you have an implementation for this
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // For filter order

@Configuration
@EnableWebSecurity
public class Config {
    @Autowired
    AdminUserDetailsService adminUserDetailsService;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Apply CORS to all paths under /api/
                        .allowedOrigins("http://localhost:3000") // Your React app's origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // *** ADD OPTIONS HERE ***
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // *** ALLOW CREDENTIALS (for Authorization header) ***
            }
        };
    }
}
