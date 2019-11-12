package com.servicedemo.app;

import com.google.gson.annotations.SerializedName;

public class RequestModel {
    @SerializedName("time")
    private String time;

    public RequestModel(String time){
        this.time = time;
    }
}
