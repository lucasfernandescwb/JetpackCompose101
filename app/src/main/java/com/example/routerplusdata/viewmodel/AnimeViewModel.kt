package com.example.routerplusdata.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routerplusdata.data.RetrofitInstance
import com.example.routerplusdata.model.Anime
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {

    var animes by mutableStateOf<List<Anime>>(emptyList())
        private set
    var singleAnime by mutableStateOf<Anime?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var isSingleAnimeLoading by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTrendingAnime()
                animes = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun getSingleAnime(id: String) {
        viewModelScope.launch {
            isSingleAnimeLoading = true

            try {
                val response = RetrofitInstance.api.getSingleAnime(id)
                singleAnime = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isSingleAnimeLoading = false
            }
        }
    }
}