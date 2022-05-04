package com.alejandrorios.peakstest.di

import com.alejandrorios.peakstest.BuildConfig
import com.alejandrorios.peakstest.data.service.PeaksService
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TIME_OUT = 30L

val dataModule = module {
    // Client
    single() {
        OkHttpClient
            .Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    single() {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Services
    single { createPeaksService(get()) }
}

fun createPeaksService(retrofit: Retrofit): PeaksService = retrofit.create(PeaksService::class.java)
