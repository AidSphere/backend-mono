package org.spring.authenticationservice.DTO.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class LoginUserDto {
    private String email;
    private String password;
}
