package com.rayllanderson.aws.anime.find.details

import com.rayllanderson.aws.anime.Anime
import com.rayllanderson.aws.anime.AnimeRepository
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@MicronautTest(transactional = false)
internal class FindAnimeDetailsControllerTest{

    @field:Inject
    @field:Client("/")
    lateinit var restClient: HttpClient

    @field:Inject
    lateinit var repository: AnimeRepository

    private val baseUrl = "/api/v1/animes"

    @Test
    fun `should find anime details` (){
        val savedAnime = repository.save(Anime("Kaguya-sama: Love is war"))

        val expectedId = savedAnime.id
        val response = restClient.toBlocking().exchange(HttpRequest.GET<Any>("${baseUrl}/$expectedId"),
            AnimeDetailsResponse::class.java)

        assertNotNull(response.body())
        assertEquals(expectedId, response.body()!!.id)
    }
}