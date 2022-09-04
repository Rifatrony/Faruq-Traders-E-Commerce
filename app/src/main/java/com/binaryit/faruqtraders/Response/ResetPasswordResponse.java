package com.binaryit.faruqtraders.Response;

public class ResetPasswordResponse {

    public String messave;

    public ResetPasswordResponse(String messave) {
        this.messave = messave;
    }

    public String getMessave() {
        return messave;
    }

    public void setMessave(String messave) {
        this.messave = messave;
    }
}
