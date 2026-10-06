package com.example.routerplusdata.data

import com.example.routerplusdata.model.Anime
import com.example.routerplusdata.model.AnimeResponse
import com.example.routerplusdata.model.SingleAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("trending/anime")
    suspend fun getTrendingAnime(): AnimeResponse

    @GET("anime/{id}")
    suspend fun getSingleAnime(@Path("id") id: String): SingleAnimeResponse
}