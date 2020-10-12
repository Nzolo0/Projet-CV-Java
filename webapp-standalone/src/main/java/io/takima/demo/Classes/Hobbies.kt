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

    override fun toString(): String {
        return "Hobbies(hob_id=$hob_id, hob_title=$hob_title, hob_details=$hob_details)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hobbies

        if (hob_id != other.hob_id) return false
        if (hob_title != other.hob_title) return false
        if (hob_details != other.hob_details) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hob_id?.hashCode() ?: 0
        result = 31 * result + (hob_title?.hashCode() ?: 0)
        result = 31 * result + (hob_details?.hashCode() ?: 0)
        return result
    }
}
