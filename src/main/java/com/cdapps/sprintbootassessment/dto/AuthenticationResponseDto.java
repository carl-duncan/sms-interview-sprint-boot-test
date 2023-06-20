package com.cdapps.sprintbootassessment.dto;

import java.util.List;

public class AuthenticationResponseDto {
    private String token;
    private long expiresIn;

    private List<String> scopes;

    public AuthenticationResponseDto(String token, long expiresIn, List<String> scopes) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.scopes = scopes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

}
