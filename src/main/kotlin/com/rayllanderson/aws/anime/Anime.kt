package com.rayllanderson.aws.anime

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class Anime(
    @Column(nullable = false) @field:NotBlank val name: String
) {
    @Id
    val id: UUID = UUID.randomUUID()
}