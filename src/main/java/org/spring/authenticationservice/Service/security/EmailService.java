package org.spring.authenticationservice.Service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmailService {

    @Autowired
    RestTemplate restTemplate;
    public String sendEmail(String emailType, Map<String, String> emailPayload) {

        // Define email API endpoints
        String endpoint = switch (emailType.toLowerCase()) {
            case "activation" -> "/api/activation";
            case "password-reset" -> "/api/password-reset";
            case "otp" -> "/api/send-otp";
            case "reset" -> "/api/reset-password";
            default -> throw new IllegalArgumentException("Invalid email type: " + emailType);
        };

        // Build full URL
        String url = "http://localhost:3002"+ endpoint;

        // Send the request
        ResponseEntity<String> response = restTemplate.postForEntity(url, emailPayload, String.class);

        return response.getBody();
    }
}
