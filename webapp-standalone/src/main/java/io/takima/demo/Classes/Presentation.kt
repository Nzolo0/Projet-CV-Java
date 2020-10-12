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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Presentation

        if (pres_id != other.pres_id) return false
        if (pres_title != other.pres_title) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pres_id?.hashCode() ?: 0
        result = 31 * result + (pres_title?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Presentation(pres_id=$pres_id, pres_title=$pres_title, description=$description)"
    }
}
