package io.takima.demo

import javax.persistence.*

/**
 *
 */
@Entity(name = "hobbies")
data class Hobbies(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var hob_id: Long?,
        @Column(name = "hob_title") var hob_title: String?,
        @Column(name = "hob_details") var hob_details: String?
) {
    constructor() : this(null, null, null)
}
