package com.rayllanderson.aws.anime

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import java.util.*
import javax.validation.constraints.NotBlank


@MicronautTest(transactional = false)
internal class SaveAnimeControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var restClient: HttpClient

    @field:Inject
    lateinit var repository: AnimeRepository

    private val baseUrl = "/api/v1/animes"

    @AfterEach
    fun cleanUp() {
        repository.deleteAll()
    }

    @Test
    fun `should save new anime`() {
        val requestBody = SaveAnimeRequest("Kaguya-sama: Love is war")

        val request = HttpRequest.POST(baseUrl, requestBody)
        val response = restClient.toBlocking().exchange(request, SaveAnimeRequest::class.java)


        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains(HttpHeaders.LOCATION))

        val returnedId = getReturnedId(response.header(HttpHeaders.LOCATION))
        val savedAnime = repository.findById(returnedId)

        assertNotNull(savedAnime)
        assertTrue(savedAnime.isPresent)
        assertEquals(returnedId, savedAnime.get().id)
    }

    @Test
    fun `should return 400 bad request when name is blank`() {
        val requestBody = SaveAnimeRequest("")

        val request = HttpRequest.POST(baseUrl, requestBody)

        val error = assertThrows<HttpClientResponseException>{
            restClient.toBlocking().exchange(request, Any::class.java)
        }

        assertEquals(HttpStatus.BAD_REQUEST, error.status)
        assertNull(error.response.header(HttpHeaders.LOCATION))

        assertTrue(repository.findAll().isEmpty())
    }

    private fun getReturnedId(header: @NotBlank String?): UUID {
        val returnedId = header!!.substring(header.lastIndexOf("/") + 1)
        return UUID.fromString(returnedId)
    }
}