package com.mindiii.lasross.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

    // DBUG BASE URL
    private static final String API_BASE_URL = "https://www.lasross.com/service/api_v1/";

    //LIVE BASE URL
    //private static final String API_BASE_URL = "https://api.lasross.com/api_v1/";

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build();

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL);

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(serviceClass);
    }

    public static Retrofit getRetrofit() {
        return builder.client(httpClient).build();
    }
}
