package com.example.myapplication.remote;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://mobile.gongu365.vn/v10/";
    private static Retrofit retrofit = null;

    static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor("your_token_here"))
            .build();

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
