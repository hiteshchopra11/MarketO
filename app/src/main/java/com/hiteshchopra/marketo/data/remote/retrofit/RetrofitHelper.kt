package com.hiteshchopra.marketo.data.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    private val okHttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun createOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor)
            .addInterceptor(AwsInterceptor()).retryOnConnectionFailure(true).build()
    }

    fun createRetrofitClient(
        okHttpClient: OkHttpClient, baseUrl: String
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }
}