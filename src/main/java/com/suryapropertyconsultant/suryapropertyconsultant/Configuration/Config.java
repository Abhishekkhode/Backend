//package com.suryapropertyconsultant.suryapropertyconsultant.Configuration;
////
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.web.servlet.config.annotation.CorsRegistry;
////import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
////
////
////@Configuration
////public class Config {
////
////    @Bean
////    public WebMvcConfigurer corsConfigurer() {
////        return new WebMvcConfigurer() {
////            @Override
////            public void addCorsMappings(CorsRegistry registry) {
////                registry.addMapping("/api/**").allowedOrigins("http://localhost:3000");
////            }
////        };
////    }
////
////}
//// src/main/java/com/suryapropertyconsultant/suryapropertyconsultant/Config.java (or config package)
//
////package com.suryapropertyconsultant.suryapropertyconsultant; // Adjust package to yours
//
//import com.suryapropertyconsultant.suryapropertyconsultant.Service.AdminUserDetailsService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.userdetails.UserDetailsService; // Make sure you have an implementation for this
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // For filter order
//
//@Configuration
//@EnableWebSecurity
//public class Config {
//    @Autowired
//    AdminUserDetailsService adminUserDetailsService;
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/api/**") // Apply CORS to all paths under /api/
//                        .allowedOrigins("http://localhost:3000") // Your React app's origin
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // *** ADD OPTIONS HERE ***
//                        .allowedHeaders("*") // Allow all headers
//                        .allowCredentials(true); // *** ALLOW CREDENTIALS (for Authorization header) ***
//            }
//        };
//    }
//}




package com.suryapropertyconsultant.suryapropertyconsultant.Configuration;

import com.suryapropertyconsultant.suryapropertyconsultant.Service.AdminUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Keep if other security-related beans are in this class
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// Keep @EnableWebSecurity here ONLY if you are defining Spring Security's main filter chain
// or other core security components *within this specific class*.
// If SecurityConfig handles all core security (which is typical and preferred),
// you might not need @EnableWebSecurity on this Config class.
public class Config {

    // Keep this @Autowired if AdminUserDetailsService is used by other beans *in this class*.
    // If it's only used in SecurityConfig, you can remove this here.
    @Autowired
    AdminUserDetailsService adminUserDetailsService;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply CORS to ALL endpoints
                        .allowedOrigins(
                                "http://localhost:3000", // Your local React app's development server
                                // *** CRITICAL: ADD YOUR ACTUAL VERCEl URL(s) HERE ***
                                "https://suryapropertyconsultant.vercel.app", // Main Vercel URL
                                "https://suryapropertyconsultant-git-main-abhis-projects-5ceacd68.vercel.app" // Vercel preview/branch URL
                                // "https://www.your-custom-domain.com" // ADD YOUR CUSTOM DOMAIN HERE IF YOU HAVE ONE
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600); // Cache pre-flight response for 1 hour
            }
        };
    }

    // IMPORTANT: If you have other Spring Security configurations (like AuthenticationManager,
    // DaoAuthenticationProvider, etc.) in this same class, they should remain here.
    // The focus here is purely on the CORS configuration part.
}