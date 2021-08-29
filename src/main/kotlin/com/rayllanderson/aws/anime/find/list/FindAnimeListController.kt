package com.rayllanderson.aws.anime.find.list

import com.rayllanderson.aws.anime.AnimeRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.validation.Validated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.transaction.Transactional

@Validated
@Controller("/api/v1/animes")
class FindAnimeListController(
    private val repository: AnimeRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Get
    @Transactional
    fun findAnimeList(): HttpResponse<Any> {
        val animes = repository.findAll()

        logger.info("Buscando todos os animes...")

        return HttpResponse.ok(AnimeListResponse.fromModelList(animes))
    }
}