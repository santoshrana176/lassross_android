package com.mindiii.lasross.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGeneratorkotlin {

    // DBUG BASE URL
    private val API_BASE_URL = "https://www.lasross.com/service/api_v1/"

    // LIVE BASE URL
    //private val API_BASE_URL = "https://api.lasross.com/api_v1/"

    private val httpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build()

    private val gson = GsonBuilder()
            .setLenient()
            .create()

    private val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)


    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder.client(httpClient).addConverterFactory(GsonConverterFactory.create(gson)).build()
        return retrofit.create(serviceClass)
    }

    fun getRetrofit(): Retrofit {
        return builder.client(httpClient).build()
    }
}