package com.smartproductions.rawnews.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

val apiToken = "DUwE2WvVR1jB28khEJDUkMECz0fCjULAZhBN1jvn"
interface ApiInterface {


    @GET("news/top")
    fun getTopNews(@Query("api_token") api_token:String = apiToken): Call<NoticiasResponse>
}
