package com.example.routerplusdata.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routerplusdata.data.RetrofitInstance
import com.example.routerplusdata.model.Anime
import kotlinx.coroutines.launch

class SingleAnimeViewModel : ViewModel() {

    var anime by mutableStateOf<Anime?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun getSingleAnime(id: String) {
        viewModelScope.launch {
            isLoading = true

            try {
                val response = RetrofitInstance.api.getSingleAnime(id)
                anime = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}