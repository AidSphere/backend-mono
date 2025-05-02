package org.spring.authenticationservice.DTO.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("role")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRoles(String roles) {
        this.role = roles;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
