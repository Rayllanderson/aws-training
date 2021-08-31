package com.rayllanderson.aws.anime.find.details

import com.rayllanderson.aws.anime.AnimeRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.validation.Validated
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import javax.transaction.Transactional

@Validated
@Controller("/api/v1/animes")
class FindAnimeDetailsController(
    private val repository: AnimeRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Get("/{id}")
    @Transactional
    fun findDetails(@PathVariable id: UUID): HttpResponse<Any> {
        val anime = repository.findById(id).orElseThrow {
            HttpClientResponseException("Anime não encontrado", HttpResponse.notFound<Any>())
        }

        logger.info("Busca detalhada realizada em $anime")

        return HttpResponse.ok(AnimeDetailsResponse.fromModel(anime))
    }
}