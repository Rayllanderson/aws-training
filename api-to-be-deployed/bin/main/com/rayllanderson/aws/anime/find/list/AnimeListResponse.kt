package com.rayllanderson.aws.anime.find.list

import com.rayllanderson.aws.anime.Anime

data class AnimeListResponse(
    val name: String
) {

    companion object {
        fun fromModelList(animes: List<Anime>): List<AnimeListResponse> {
            return animes.map { AnimeListResponse(it.name) }
        }
    }
}
