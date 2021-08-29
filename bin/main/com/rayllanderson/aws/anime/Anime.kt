package com.rayllanderson.aws.anime

import java.util.*
import javax.persistence.Column
import javax.persistence.Id
import javax.validation.constraints.NotBlank

class Anime(
    @Column(nullable = false) @field:NotBlank val name: String
) {
    @Id
    var id: UUID = UUID.randomUUID()
}