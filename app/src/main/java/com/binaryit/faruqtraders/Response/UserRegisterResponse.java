package com.binaryit.faruqtraders.Response;

public class UserRegisterResponse {

    String access_token, token_type;

    public UserRegisterResponse() {
    }

    public UserRegisterResponse(String access_token, String token_type) {
        this.access_token = access_token;
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

}
