package com.example.myapplication.model;

public class LoginResponse {
    public String token;
    public Object refreshToken;
    public UserModel customerAccount;
    public boolean passwordExpired;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(Object refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserModel getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(UserModel customerAccount) {
        this.customerAccount = customerAccount;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }


}
