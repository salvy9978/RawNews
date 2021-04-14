package com.smartproductions.rawnews.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartproductions.rawnews.models.NoticiasResponse
import com.smartproductions.rawnews.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MostrarNoticiasByCategoriaViewModel(private val repository: Repository) : ViewModel() {

    val lvdNewsResponse : MutableLiveData<Response<NoticiasResponse>> = MutableLiveData()

    fun getNewsByCategory(page : Int, language : String, category: String){
        viewModelScope.launch {
            val response = repository.getNewsByCategory(page = page, language = language, category = category)
            lvdNewsResponse.value = response
        }
    }

}