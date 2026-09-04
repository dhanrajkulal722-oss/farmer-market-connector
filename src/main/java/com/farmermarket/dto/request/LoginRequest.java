package com.farmermarket.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    // ==========================
    // GETTERS
    // ==========================

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // ==========================
    // SETTERS
    // ==========================

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
