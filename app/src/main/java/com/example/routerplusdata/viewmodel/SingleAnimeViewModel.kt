package com.example.routerplusdata.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routerplusdata.data.RetrofitInstance
import com.example.routerplusdata.model.Anime
import com.example.routerplusdata.model.CharacterItem
import com.example.routerplusdata.model.toCharacterItems
import kotlinx.coroutines.launch

class SingleAnimeViewModel : ViewModel() {

    var anime by mutableStateOf<Anime?>(null)
        private set

    var characters by mutableStateOf<List<CharacterItem>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var isCharactersLoading by mutableStateOf(false)
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

        viewModelScope.launch {
            isCharactersLoading = true

            try {
                var response = RetrofitInstance.api.getAnimeCharacters(animeId = id)
                if (response.data.isNullOrEmpty()) {
                    response = RetrofitInstance.api.getAnimeCharactersByPath(id = id)
                }
                characters = response.toCharacterItems()
            } catch (e: Exception) {
                e.printStackTrace()
                try {
                    val response = RetrofitInstance.api.getAnimeCharactersByPath(id = id)
                    characters = response.toCharacterItems()
                } catch (e2: Exception) {
                    e2.printStackTrace()
                }
            } finally {
                isCharactersLoading = false
            }
        }
    }
}
