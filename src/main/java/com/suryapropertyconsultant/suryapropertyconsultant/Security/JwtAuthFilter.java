//package com.suryapropertyconsultant.suryapropertyconsultant.Security;
//
//import com.suryapropertyconsultant.suryapropertyconsultant.Utility.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component; // Import this
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//class JwtAuthFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//
//    public JwtAuthFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//
//            if (jwtUtil.validateToken(token)) {
//                String username = jwtUtil.extractUsername(token);
//                // You can add role info here
//                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
//                        username, null, List.of());
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}
package com.suryapropertyconsultant.suryapropertyconsultant.Security;

import com.suryapropertyconsultant.suryapropertyconsultant.Utility.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Import for roles

import java.io.IOException;
import java.util.Collections; // Import for Collections.singletonList
import java.util.List;

/**
 * Custom JWT Authentication Filter.
 * This filter intercepts incoming requests to validate JWT tokens
 * and set the authentication context in Spring Security.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    /**
     * Constructor for JwtAuthFilter.
     * @param jwtUtil The JwtUtil instance for token operations.
     */
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Performs the actual filtering logic for each request.
     * It extracts the JWT from the Authorization header, validates it,
     * and sets the authenticated user in the SecurityContextHolder.
     *
     * @param request       The HttpServletRequest object.
     * @param response      The HttpServletResponse object.
     * @param filterChain   The FilterChain for proceeding with the request.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // Check if the header exists and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the token by removing the "Bearer " prefix
            String token = authHeader.substring(7);

            // Validate the token using JwtUtil
            if (jwtUtil.validateToken(token)) {
                // If token is valid, extract the username (email) from the token
                String username = jwtUtil.extractUsername(token);

                // Extract the role from the token's claims.
                // Assuming 'role' is a claim stored in the token when it was generated.
                String role = jwtUtil.extractClaim(token, claims -> claims.get("role", String.class));

                // Create a list of authorities (roles) for the authenticated user.
                // Spring Security roles should be prefixed with "ROLE_".
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

                // Create an Authentication object (UsernamePasswordAuthenticationToken).
                // The principal is the username, credentials are null (as it's a token-based auth),
                // and authorities are the roles extracted from the token.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, authorities);

                // Set the Authentication object in the SecurityContextHolder.
                // This marks the user as authenticated for the current request context.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continue the filter chain. If the token was valid, the user is now authenticated.
        // If not, the request will proceed, but access to protected resources will be denied
        // by subsequent Spring Security filters based on the 'anyRequest().authenticated()' rule.
        filterChain.doFilter(request, response);

    }

}

