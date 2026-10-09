package com.example.routerplusdata.data

import com.example.routerplusdata.model.AnimeCharactersResponse
import com.example.routerplusdata.model.AnimeResponse
import com.example.routerplusdata.model.SingleAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("trending/anime")
    suspend fun getTrendingAnime(): AnimeResponse

    @GET("anime")
    suspend fun getAnimeList(
        @Query("page[limit]") limit: Int = 20,
        @Query("page[offset]") offset: Int = 0,
        @Query("sort") sort: String? = "-userCount",
        @Query("filter[text]") text: String? = null,
    ): AnimeResponse

    @GET("anime/{id}")
    suspend fun getSingleAnime(@Path("id") id: String): SingleAnimeResponse

    @GET("anime-characters")
    suspend fun getAnimeCharacters(
        @Query("filter[animeId]") animeId: String,
        @Query("include") include: String = "character",
    ): AnimeCharactersResponse

    @GET("anime/{id}/anime-characters")
    suspend fun getAnimeCharactersByPath(
        @Path("id") id: String,
        @Query("include") include: String = "character",
    ): AnimeCharactersResponse
}
