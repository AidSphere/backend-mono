package org.spring.authenticationservice.service;

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

    @Value("${EMAIL_SERVICE_NAME}")
    private String emailServiceName;

    public String sendEmail(String emailType, Map<String, String> emailPayload) {
        System.out.println(emailServiceName);

        // Define email API endpoints
        String endpoint;
        switch (emailType.toLowerCase()) {
            case "activation":
                endpoint = "/api/v1/activation";
                break;
            case "password-reset":
                endpoint = "/api/v1/password-reset";
                break;
            case "otp":
                endpoint = "/api/v1/send-otp";
                break;
            default:
                throw new IllegalArgumentException("Invalid email type: " + emailType);
        }

        // Build full URL
        String url = "http://" + "localhost:8080" + endpoint;

        // Send the request
        ResponseEntity<String> response = restTemplate.postForEntity(url, emailPayload, String.class);

        return response.getBody();
    }
}
