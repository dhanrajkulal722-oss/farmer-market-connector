package com.farmermarket.dto.response;

public class LoginResponse {

    private String token;

    private String tokenType;

    // ==============================
    // CONSTRUCTORS
    // ==============================

    public LoginResponse() {
    }

    public LoginResponse(
            String token,
            String tokenType) {

        this.token = token;
        this.tokenType = tokenType;
    }

    // ==============================
    // GETTERS
    // ==============================

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    // ==============================
    // SETTERS
    // ==============================

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenType(
            String tokenType) {

        this.tokenType = tokenType;
    }
}