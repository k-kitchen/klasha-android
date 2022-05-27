package com.klasha.android.service

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal object ApiFactory {
    private const val BASE_URL = "https://ktests.com/"

    fun createService(context: Context, token: String): ApiService{

        val gson = GsonBuilder().setLenient().create()
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor(context))
            .addInterceptor {chain ->
                val inComing = chain.request()
                val outGoing = inComing.newBuilder()
                    .header("x-auth-token", token)
                    .method(inComing.method(), inComing.body())
                    .build()
                chain.proceed(outGoing)
            }
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ApiService::class.java)
    }
}