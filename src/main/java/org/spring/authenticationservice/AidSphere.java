package org.spring.authenticationservice;

import org.spring.authenticationservice.config.OpenApiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(OpenApiConfig.class)
public class AidSphere {

    public static void main(String[] args) {
        SpringApplication.run(AidSphere.class, args);
    }

}
