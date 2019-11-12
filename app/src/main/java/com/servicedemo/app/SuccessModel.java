package com.servicedemo.app;

import com.google.gson.annotations.SerializedName;

public class SuccessModel {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
