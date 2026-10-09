package com.example.routerplusdata.model

data class AnimeCharactersResponse(
    val data: List<AnimeCharacterData>? = null,
    val included: List<IncludedCharacter>? = null,
)

data class AnimeCharacterData(
    val id: String,
    val type: String,
    val attributes: AnimeCharacterAttributes? = null,
    val relationships: CharacterRelationships? = null,
)

data class AnimeCharacterAttributes(
    val role: String? = null,
)

data class CharacterRelationships(
    val character: RelatedCharacterData? = null,
)

data class RelatedCharacterData(
    val data: CharacterIdData? = null,
)

data class CharacterIdData(
    val id: String,
    val type: String,
)

data class IncludedCharacter(
    val id: String,
    val type: String,
    val attributes: CharacterAttributes? = null,
)

data class CharacterAttributes(
    val name: String? = null,
    val canonicalName: String? = null,
    val slug: String? = null,
    val description: String? = null,
    val image: CharacterImage? = null,
)

data class CharacterImage(
    val original: String? = null,
    val tiny: String? = null,
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
)

data class CharacterItem(
    val id: String,
    val name: String,
    val role: String,
    val imageUrl: String?,
)

fun AnimeCharactersResponse.toCharacterItems(): List<CharacterItem> {
    val includedMap = included
        ?.filter { it.type.startsWith("character") && (it.attributes != null) }
        ?.associateBy { it.id }
        ?: emptyMap()

    return data?.mapNotNull { animeChar ->
        val charId = animeChar.relationships?.character?.data?.id
        val character = charId?.let { includedMap[it] }
        val name = character?.attributes?.canonicalName
            ?: character?.attributes?.name
            ?: character?.attributes?.slug

        if (!name.isNullOrBlank()) {
            CharacterItem(
                id = animeChar.id,
                name = name,
                role = animeChar.attributes?.role?.replaceFirstChar { it.uppercase() } ?: "Main",
                imageUrl = character?.attributes?.image?.original
                    ?: character?.attributes?.image?.medium
                    ?: character?.attributes?.image?.large
                    ?: character?.attributes?.image?.small,
            )
        } else {
            null
        }
    } ?: emptyList()
}
