package com.example.myapplication.remote;

import com.example.myapplication.model.BestProductResponse;
import com.example.myapplication.model.LoginParams;
import com.example.myapplication.model.LoginResponse;
import com.example.myapplication.model.ProductModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("api/account/login-with-pass")
    Call<LoginResponse> login(@Body LoginParams params);

    @GET("api/public/product/best?page=0&size=10")
    Call<BestProductResponse> getBestProduct();
}
