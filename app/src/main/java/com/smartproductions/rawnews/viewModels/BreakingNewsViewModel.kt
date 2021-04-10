package com.smartproductions.rawnews.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartproductions.rawnews.models.NoticiasResponse
import com.smartproductions.rawnews.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class BreakingNewsViewModel(private val repository: Repository) : ViewModel() {

    val lvdNewsResponse : MutableLiveData<Response<NoticiasResponse>> = MutableLiveData()

    fun getTopNews(){
        viewModelScope.launch {
            val response = repository.getTopNews()
            lvdNewsResponse.value = response
        }
    }

    fun getTopNews(page : Int){
        viewModelScope.launch {
            val response = repository.getTopNews(page = page)
            lvdNewsResponse.value = response
        }
    }

    fun getTopNews(page : Int, language : String){
        viewModelScope.launch {
            val response = repository.getTopNews(page = page, language = language)
            lvdNewsResponse.value = response
        }
    }

}