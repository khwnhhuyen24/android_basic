package com.example.myapplication.model;

public class RootModel{
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

    public LoginResponse getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(LoginResponse customerAccount) {
        this.customerAccount = customerAccount;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public String token;
    public Object refreshToken;
    public LoginResponse customerAccount;
    public boolean passwordExpired;
}
