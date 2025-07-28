package com.example.myapplication.remote;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
//    private String token;

    public AuthInterceptor(String token) {
//        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("X-RSA-TOKEN", "H0IuSe4XJ8kAGDNd/kDDbvnQ18u7zn1/CVl3V5OzMR/GGtMy26ipl1QyjmXFpnn+8mmCtQ1KytYp/i4LbApBSQ==" )
                .header("Accept", "application/json")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dW5ndnUxMSIsImxvZ2luVHlwZSI6IktPTF9BQ0NPVU5UIiwiaXNzIjoid3d3LmJlc291bC5jb20iLCJpYXQiOjE3NTI5Mzg2MzQsImV4cCI6MjA2Mzk3ODYzNH0.oCiSh9OSOoEVcS2HGVenR2N4DI2nAu3o2kFsBoO6k9k")
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
