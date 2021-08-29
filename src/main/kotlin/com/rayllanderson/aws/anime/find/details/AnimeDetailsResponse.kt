package com.rayllanderson.aws.anime.find.details

import com.rayllanderson.aws.anime.Anime
import java.util.*

data class AnimeDetailsResponse(
    val id: UUID,
    val name: String
) {

    companion object {
        fun fromModel(anime: Anime): AnimeDetailsResponse {
            return AnimeDetailsResponse(anime.id, anime.name)
        }
    }
}
