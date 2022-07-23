package com.binaryit.faruqtraders.Response;

public class OrderResponse {

    public Success success;

    public OrderResponse() {
    }

    public OrderResponse(Success success) {
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
