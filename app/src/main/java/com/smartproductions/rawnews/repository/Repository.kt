package com.smartproductions.rawnews.repository

import com.smartproductions.rawnews.api.NoticiasRetrofitInstance
import com.smartproductions.rawnews.models.NoticiasResponse
import com.smartproductions.rawnews.util.CountryCheck
import retrofit2.Response

class Repository {

    suspend fun getTopNews():Response<NoticiasResponse>{
        return NoticiasRetrofitInstance.apiNoticias.getTopNews()
    }

    suspend fun getTopNews(page: Int):Response<NoticiasResponse>{
        return NoticiasRetrofitInstance.apiNoticias.getTopNews(page = page)
    }

    suspend fun getTopNews(page: Int, locale: String):Response<NoticiasResponse>{
        val localeChecked = CountryCheck().localeCheck(locale)
        return NoticiasRetrofitInstance.apiNoticias.getTopNews(page = page, locale = localeChecked)
    }

    suspend fun getNewsByCategory(page: Int, language: String, category: String):Response<NoticiasResponse>{
        val languageChecked = CountryCheck().languageCheck(language)
        return NoticiasRetrofitInstance.apiNoticias.getNewsByCategory(page = page, language = languageChecked, category = category)
    }


}