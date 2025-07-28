package com.example.myapplication.model;

public class LogoutParams {
    private String deviceId;

    public LogoutParams(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
