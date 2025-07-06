package com.suryapropertyconsultant.suryapropertyconsultant.Dto;


import com.suryapropertyconsultant.suryapropertyconsultant.Entity.AdminUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDTO {
    private String id;
    private String email;
    private String role;
    private String Password;

    public AdminUserDTO(AdminUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
