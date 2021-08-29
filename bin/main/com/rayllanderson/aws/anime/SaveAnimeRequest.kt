package com.rayllanderson.aws.anime

import javax.validation.constraints.NotBlank

class SaveAnimeRequest(
  @field:NotBlank val name: String
){
  fun toModel(): Anime{
    return Anime(name)
  }
}
