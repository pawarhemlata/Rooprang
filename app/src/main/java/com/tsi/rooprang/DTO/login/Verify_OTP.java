package com.tsi.rooprang.DTO.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Verify_OTP {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}