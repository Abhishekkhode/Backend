//    package com.suryapropertyconsultant.suryapropertyconsultant.Security;
//
//    import com.mongodb.ServerCursor;
//    import com.suryapropertyconsultant.suryapropertyconsultant.Service.AdminUserDetailsService; // Import your AdminUserDetailsService
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.context.annotation.Bean;
//    import org.springframework.context.annotation.Configuration;
//    import org.springframework.security.authentication.AuthenticationManager; // Import this
//    import org.springframework.security.authentication.ProviderManager; // Import this
//    import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Import this
//    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//    import org.springframework.security.config.http.SessionCreationPolicy;
//    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//    import org.springframework.security.crypto.password.PasswordEncoder;
//    import org.springframework.security.web.SecurityFilterChain;
//    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//    import org.springframework.web.cors.CorsConfiguration;
//    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//    import org.springframework.web.filter.CorsFilter;
//
//    @Configuration
//    @EnableWebSecurity
//    @EnableMethodSecurity
//    public class SecurityConfig {
//
//        private final JwtAuthFilter jwtAuthFilter; // Your JWT filter
//        private final AdminUserDetailsService adminUserDetailsService; // Inject your custom UserDetailsService
//        public SecurityConfig(JwtAuthFilter jwtAuthFilter,AdminUserDetailsService adminUserDetailsService ){
//            this.jwtAuthFilter = jwtAuthFilter;
//            this.adminUserDetailsService = adminUserDetailsService;
//        }
//
//        // Defines the security filter chain for HTTP requests
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .csrf(csrf -> csrf.disable())
//                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                    .authorizeHttpRequests(authorize -> authorize
//    //                        .requestMatchers("/api/auth/login").permitAll()
//    //                        .requestMatchers("/api/auth/validate").permitAll()
//    //                        .requestMatchers("/api/properties").permitAll()
//    //                        .requestMatchers("/api/auth").permitAll()// Added validate to permitAll for easier testing of token validation
//                                    .requestMatchers("/actuator/health").permitAll()
//                                    .requestMatchers("/actuator").permitAll()
//                                    .requestMatchers("/health").permitAll()
//                                    .requestMatchers("/api/auth/**").permitAll()
//                                    .requestMatchers("/api/support/contact").permitAll()
//                                    .requestMatchers("/api/support/**").permitAll()
//                                    .requestMatchers("/api/agents").permitAll()
//
//                                    .requestMatchers("/api/auth/login", "/api/auth/validate").permitAll()
////                                    .requestMatchers("/api/**").authenticated()
//
//                                    .requestMatchers("/api/properties").permitAll()
//                                    .requestMatchers(
//                                            "/api/properties", // GET all properties - keep public
//                                            "/api/properties/{id}" // GET property by ID - keep public
//                                    ).permitAll()
//                                    .anyRequest().authenticated()
//                    )
//                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                    .authenticationProvider(authenticationProvider());
//
//            // Add the JWT filter before Spring Security's default UsernamePasswordAuthenticationFilter
//            http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//    //        http.authenticationProvider(authenticationProvider());
//
//            return http.build();
//        }
//        @Bean
//        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//            return http.getSharedObject(AuthenticationManagerBuilder.class)
//                    .userDetailsService(adminUserDetailsService)
//                    .passwordEncoder(passwordEncoder())
//                    .and()
//                    .build();
//        }
////@Bean
////public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
////    return config.getAuthenticationManager();
////}
//
//        @Bean
//        public BCryptPasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }
//
//        // CORS Configuration Bean
//        @Bean
//        public CorsFilter corsFilter() {
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            CorsConfiguration config = new CorsConfiguration();
//            config.setAllowCredentials(true);
//            config.addAllowedOrigin("http://localhost:3000");
//            config.addAllowedHeader("*");
//            config.addAllowedMethod("*");
//            source.registerCorsConfiguration("/**", config);
//            return new CorsFilter(source);
//        }
//
//        // Helper method for CORS configuration source
//        private UrlBasedCorsConfigurationSource corsConfigurationSource() {
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            CorsConfiguration config = new CorsConfiguration();
//            config.setAllowCredentials(true);
//            config.addAllowedOrigin("http://localhost:3000");
//            config.addAllowedHeader("*");
//            config.addAllowedMethod("*");
//            source.registerCorsConfiguration("/**", config);
//            return source;
//        }
//        @Bean
//        public DaoAuthenticationProvider authenticationProvider() {
//            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//            authProvider.setUserDetailsService(adminUserDetailsService);
//            authProvider.setPasswordEncoder(passwordEncoder());
//            return authProvider;
//        }
//
//    }











package com.suryapropertyconsultant.suryapropertyconsultant.Security;

import com.suryapropertyconsultant.suryapropertyconsultant.Service.AdminUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer; // Import Customizer for cleaner Cors config
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AdminUserDetailsService adminUserDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, AdminUserDetailsService adminUserDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.adminUserDetailsService = adminUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // *** THIS IS THE KEY HERE: Use Customizer.withDefaults() ***
                // This tells Spring Security to use the CorsConfigurationSource bean
                // provided by your separate WebMvcConfigurer (Config.java)
                .cors(Customizer.withDefaults())

                .authorizeHttpRequests(authorize -> authorize
                        // Public endpoints (no authentication required)
                        .requestMatchers(
                                "/",
                                "/api/auth/**",
                                "/api/support/**",
                                "/api/agents/**",
                                "/api/properties/**",
                                "/actuator/**",
                                "/health"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // *** REMOVE ANY CORS-related BEANS OR METHODS FROM HERE ***
    // (e.g., CorsFilter corsFilter(), UrlBasedCorsConfigurationSource corsConfigurationSource())
    // They are now handled exclusively by your separate Config.java class.
}