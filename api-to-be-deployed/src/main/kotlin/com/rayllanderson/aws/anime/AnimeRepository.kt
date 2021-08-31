package com.rayllanderson.aws.anime

import com.rayllanderson.aws.anime.Anime
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface AnimeRepository: JpaRepository<Anime, UUID>