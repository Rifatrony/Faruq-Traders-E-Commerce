package com.binaryit.faruqtraders.Response;

public class LoginResponse {

    String message;

    public LoginResponse(String message) {
        this.message = message;
    }

    public LoginResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
