package com.smartproductions.rawnews.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "https://api.thenewsapi.com"
val version="/v1/"

data class ApiNoticias(
        var retrofit: Retrofit?
){
    fun getClient(): ApiInterface? {

        // change your base URL
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL+ version)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        //Creating object for our interface
        return retrofit!!.create(ApiInterface::class.java) // return the APIInterface object
    }
}


