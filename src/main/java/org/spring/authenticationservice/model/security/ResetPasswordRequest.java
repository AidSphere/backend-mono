package org.spring.authenticationservice.model.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String email;
    private String newPassword;

    public ResetPasswordRequest(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }

}
