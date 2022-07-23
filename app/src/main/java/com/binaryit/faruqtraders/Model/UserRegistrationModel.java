package com.binaryit.faruqtraders.Model;

public class UserRegistrationModel {

    String name, phone, email,
            password, password_confirmation, device_name;

    public UserRegistrationModel(String name, String phone, String email, String password, String password_confirmation, String device_name) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.device_name = device_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }
}
