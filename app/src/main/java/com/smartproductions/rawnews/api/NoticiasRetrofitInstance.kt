package com.smartproductions.rawnews.api

import com.smartproductions.rawnews.util.Constants.Companion.NOTICIAS_API_URL
import com.smartproductions.rawnews.util.Constants.Companion.VERSION
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NoticiasRetrofitInstance {



    val certificatePinner = CertificatePinner.Builder().
        add("api.thenewsapi.com", "sha256/saq/HgjCoNn90tIMVzEG1/0e5+LwEuEwUPA8ZzDxwjA=").build()

    val okHttpClient = OkHttpClient.Builder().certificatePinner(certificatePinner).build()

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(NOTICIAS_API_URL+ VERSION )
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    val apiNoticias: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }


}