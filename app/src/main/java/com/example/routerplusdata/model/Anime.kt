package com.example.routerplusdata.model

data class AnimeResponse(
    val data: List<Anime>
)

data class SingleAnimeResponse(
    val data: Anime
)

data class Anime(
    val id: String,
    val type: String,
    val attributes: AnimeAttributes
)

data class AnimeAttributes(
    val slug: String,
    val synopsis: String,
    val canonicalTitle: String,
    val startDate: String?,
    val endDate: String?,
    val popularityRank: Int,
    val status: String,
    val episodeCount: Int,
    val coverImage: CoverImage?
)

data class CoverImage(
    val tiny: String,
    val small: String,
    val large: String,
    val original: String,
)