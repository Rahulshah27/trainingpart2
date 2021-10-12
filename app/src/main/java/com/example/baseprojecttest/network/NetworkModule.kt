package com.example.baseprojecttest.network

import android.content.Context
import com.example.baseprojecttest.BuildConfig
import com.example.baseprojecttest.common.BaseApp
import com.example.baseprojecttest.data.remote.DogsService
import com.example.baseprojecttest.network.adapter.NullToEmptyStringAdapter
import com.example.baseprojecttest.network.adapter.StringToBooleanAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {



    fun makeApiService(): DogsService = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .client(okHttpClient(BaseApp.getAppContext()).build())
        .addConverterFactory(MoshiConverterFactory.create(moshiFactory()))
        .build()
        .create(DogsService::class.java)


    private fun moshiFactory(): Moshi {
        return Moshi.Builder()
            .add(StringToBooleanAdapter())
            .add(NullToEmptyStringAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }


    private fun okHttpClient(applicationContext: BaseApp) =
        okHttpBuilder(applicationContext)

    private fun okHttpBuilder(context: Context) = OkHttpClient.Builder()
        .addInterceptor(makeLoggingInterceptor())
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)


    private fun makeLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

}