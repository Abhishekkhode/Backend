package com.suryapropertyconsultant.suryapropertyconsultant.Service;

import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final AdminUser user;

    public CustomUserDetails(AdminUser user) {
        this.user = user;
    }

    public AdminUser getUser() {
        return user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can add roles here if needed
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

