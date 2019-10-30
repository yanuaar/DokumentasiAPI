package com.example.retrofit;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    String status;
    @SerializedName("response")
    String response;

    public String getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }
}
