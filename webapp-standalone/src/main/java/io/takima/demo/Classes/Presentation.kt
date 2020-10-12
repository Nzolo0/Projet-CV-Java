package io.takima.demo

import javax.persistence.*

/**
 *
 */
@Entity(name = "presentation")
data class Presentation(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var pres_id: Long?,
        @Column(name = "pres_title") var pres_title: String?,
        @Column(name = "description") var description: String?
) {
    constructor() : this(null, null, null)
}
