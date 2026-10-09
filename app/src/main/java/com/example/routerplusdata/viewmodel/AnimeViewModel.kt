package com.example.routerplusdata.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routerplusdata.data.RetrofitInstance
import com.example.routerplusdata.model.Anime
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {

    var animes by mutableStateOf<List<Anime>>(emptyList())
        private set

    var searchQuery by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(true)
        private set

    var isLoadingMore by mutableStateOf(false)
        private set

    var canLoadMore by mutableStateOf(true)
        private set

    private var currentOffset = 0
    private val pageSize = 20
    private var searchJob: Job? = null

    init {
        loadInitialAnimes()
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400L)
            performSearch()
        }
    }

    fun clearSearch() {
        searchQuery = ""
        searchJob?.cancel()
        loadInitialAnimes()
    }

    fun performSearch() {
        searchJob?.cancel()
        if (searchQuery.isBlank()) {
            loadInitialAnimes()
            return
        }

        viewModelScope.launch {
            isLoading = true
            currentOffset = 0
            try {
                val response = RetrofitInstance.api.getAnimeList(
                    limit = pageSize,
                    offset = 0,
                    sort = null,
                    text = searchQuery,
                )
                animes = response.data
                currentOffset = response.data.size
                canLoadMore = response.data.size >= pageSize
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun loadInitialAnimes() {
        viewModelScope.launch {
            isLoading = true
            currentOffset = 0
            try {
                val response = RetrofitInstance.api.getTrendingAnime()
                animes = response.data
                currentOffset = response.data.size
                canLoadMore = response.data.isNotEmpty()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun loadMoreAnimes() {
        if (isLoading || isLoadingMore || !canLoadMore) return

        viewModelScope.launch {
            isLoadingMore = true
            try {
                val response = if (searchQuery.isNotBlank()) {
                    RetrofitInstance.api.getAnimeList(
                        limit = pageSize,
                        offset = currentOffset,
                        sort = null,
                        text = searchQuery,
                    )
                } else {
                    RetrofitInstance.api.getAnimeList(
                        limit = pageSize,
                        offset = currentOffset,
                        sort = "-userCount",
                    )
                }

                val newAnimes = response.data
                if (newAnimes.isNotEmpty()) {
                    val existingIds = animes.map { it.id }.toSet()
                    val uniqueNewAnimes = newAnimes.filter { it.id !in existingIds }
                    if (uniqueNewAnimes.isNotEmpty()) {
                        animes += uniqueNewAnimes
                    }
                    currentOffset += newAnimes.size
                    canLoadMore = newAnimes.size >= pageSize
                } else {
                    canLoadMore = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoadingMore = false
            }
        }
    }
}
