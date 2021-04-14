package com.smartproductions.rawnews.api

import com.smartproductions.rawnews.models.NoticiasResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val apiToken = "DUwE2WvVR1jB28khEJDUkMECz0fCjULAZhBN1jvn"
interface ApiInterface {


    @GET("news/top")
    suspend fun getTopNews(@Query("api_token") api_token:String = apiToken): Response<NoticiasResponse>

    @GET("news/top")
    suspend fun getTopNews(@Query("api_token") api_token:String = apiToken, @Query("page") page:Int): Response<NoticiasResponse>

    @GET("news/top")
    suspend fun getTopNews(@Query("api_token") api_token:String = apiToken,
                           @Query("page") page:Int,
                           @Query("locale") locale:String): Response<NoticiasResponse>

    @GET("news/all")
    suspend fun getNewsByCategory(@Query("api_token") api_token:String = apiToken,
                                  @Query("page") page:Int,
                                  @Query("language") language:String,
                                  @Query("categories") category:String): Response<NoticiasResponse>
}
