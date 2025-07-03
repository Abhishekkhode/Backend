package com.suryapropertyconsultant.suryapropertyconsultant.Entity;

public class AdminUserDTO {
    private String id;
    private String email;
    private String role;

    public AdminUserDTO(AdminUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
