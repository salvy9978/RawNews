package com.smartproductions.rawnews.repository

import com.smartproductions.rawnews.api.NoticiasRetrofitInstance
import com.smartproductions.rawnews.models.NoticiasResponse
import retrofit2.Response

class Repository {

    suspend fun getTopNews():Response<NoticiasResponse>{
        return NoticiasRetrofitInstance.apiNoticias.getTopNews()
    }


}