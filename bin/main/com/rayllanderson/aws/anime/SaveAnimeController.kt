package com.rayllanderson.aws.anime

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Body
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/api/v1/animes")
class SaveAnimeController(

) {

    @Post
    fun saveAnime(@Body @Valid request: SaveAnimeRequest) {
        request.toModel()
    }
}