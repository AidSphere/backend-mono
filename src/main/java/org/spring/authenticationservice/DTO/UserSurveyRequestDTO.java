package org.spring.authenticationservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSurveyRequestDTO {

    @JsonProperty("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
