package com.rayllanderson.aws.anime.save

import com.rayllanderson.aws.anime.AnimeRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Body
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/api/v1/animes")
class SaveAnimeController(
    private val repository: AnimeRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Post
    @Transactional
    fun saveAnime(@Body @Valid request: SaveAnimeRequest): HttpResponse<Any> {
        logger.info("Salvando novo anime $request")

        val animeToBeSaved = request.toModel()

        repository.save(animeToBeSaved)

        val uri = UriBuilder.of("/api/v1/animes/{id}").expand(mutableMapOf("id" to animeToBeSaved.id))

        logger.info("Anime salvo com id ${animeToBeSaved.id}")

        return HttpResponse.created(uri)
    }
}