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
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
