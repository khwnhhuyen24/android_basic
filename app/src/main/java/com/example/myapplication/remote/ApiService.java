package com.example.myapplication.remote;

import com.example.myapplication.model.BestProductResponse;
import com.example.myapplication.model.KolAccountModel;
import com.example.myapplication.model.LoginParams;
import com.example.myapplication.model.LoginResponse;
import com.example.myapplication.model.LogoutParams;
import com.example.myapplication.model.ProductModel;
import com.example.myapplication.model.RootBrand;
import com.example.myapplication.model.RootKol;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/account/login-with-pass")
    Call<LoginResponse> login(@Body LoginParams params);

    @GET("api/public/product/best?page=0&size=10")
    Call<BestProductResponse> getBestProduct();

    @GET("api/public/product/find-by-id")
    Call<ProductModel> getProductById(@Query("productId") int id);

    @GET("api/kol/get-info")
    Call<KolAccountModel> getKolInfo();

    @GET("api/kol/get-follower")
    Call<RootKol> getKolsIFollow();

    @GET("api/customer/get-brand-follows")
    Call<RootBrand> getBrandFollow();

    @PUT("api/customer/logout")
    Call<Void> logout(@Body LogoutParams params);
}
