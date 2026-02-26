package com.developer.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRequest {
    @NotBlank(message = "Name cannot be blank.")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email format is invalid.")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Role cannot be blank.")
    @JsonProperty("role")
    private String role;

    public UserRequest() {
    }

    public UserRequest(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
