package com.rayllanderson.aws.anime.find.list

import com.rayllanderson.aws.anime.Anime
import com.rayllanderson.aws.anime.AnimeRepository
import com.rayllanderson.aws.anime.find.details.AnimeDetailsResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@MicronautTest(transactional = false)
internal class FindAnimeListControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var restClient: HttpClient

    @field:Inject
    lateinit var repository: AnimeRepository

    private val baseUrl = "/api/v1/animes"

    @Test
    fun `should find anime list` (){
        val expectedSize = repository.saveAll(
            arrayListOf(
                Anime("Kaguya-sama: Love is war"),
                Anime("One Piece"),
                Anime("A Place Further than the Universe"),
            )
        ).size

        val response = restClient.toBlocking().exchange(
            HttpRequest.GET<Any>(baseUrl), List::class.java)

        assertNotNull(response.body())
        assertEquals(expectedSize, response.body()!!.size)
    }

    @Test
    fun `should return empty list when there's no anime saved` (){
        val response = restClient.toBlocking().exchange(
            HttpRequest.GET<Any>(baseUrl), List::class.java)

        assertNotNull(response.body())
        assertTrue(response.body()!!.isEmpty())
    }
}