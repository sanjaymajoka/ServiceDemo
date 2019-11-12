package com.servicedemo.app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/apis/postTest")
    Call<SuccessModel> getPostTest(@Body RequestModel model);
}
