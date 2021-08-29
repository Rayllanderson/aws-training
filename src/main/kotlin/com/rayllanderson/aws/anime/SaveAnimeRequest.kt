package com.rayllanderson.aws.anime

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class SaveAnimeRequest(
  @field:NotBlank val name: String
){
  fun toModel(): Anime{
    return Anime(name)
  }
}
