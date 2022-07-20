package com.example.faruqtraders.Response;

public class DeleteResponse {

    public Success success;

    public DeleteResponse() {
    }

    public DeleteResponse(Success success) {
        this.success = success;
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public class Success{
        public String message;
    }
}
