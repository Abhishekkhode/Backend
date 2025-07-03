package com.suryapropertyconsultant.suryapropertyconsultant.Entity;

//package com.example.demo.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admin_users")
@Data
@Getter
@Setter
public class AdminUser {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
//    private String username;
    private String role; // e.g. "ADMIN"
    private String password;
    public AdminUser() {
    }

    // Existing parameterized constructor
    public AdminUser(String email, String password, String username, String role) {
        this.email = email;
        this.password = password;
//        this.username = username;
        this.role = role;
    }
}
