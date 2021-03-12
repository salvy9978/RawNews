package com.smartproductions.rawnews.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smartproductions.rawnews.repository.Repository

class BreakingnewsViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BreakingNewsViewModel(repository) as T
    }
}