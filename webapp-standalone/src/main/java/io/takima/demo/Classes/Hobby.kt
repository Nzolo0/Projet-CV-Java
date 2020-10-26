package io.takima.demo

import javax.persistence.*

/**
 *
 */
@Entity(name = "hobbies")
data class Hobby(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var id: Long?,
        @Column(name = "title") var title: String?,
        @Column(name = "details") var details: String?
) :Comparable<Hobby> {
    constructor() : this(null, null, null)

    override fun toString(): String {
        return "Hobby(id=$id, title=$title, details=$details)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hobby

        if (id != other.id) return false
        if (title != other.title) return false
        if (details != other.details) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (details?.hashCode() ?: 0)
        return result
    }

    override fun compareTo(other: Hobby): Int {

        if (title.isNullOrEmpty() && other.title.isNullOrEmpty())
            return 0
        if (title.isNullOrEmpty()) return -1
        if (other.title.isNullOrEmpty()) return 1
        return title!!.compareTo(other.title!!)

    }
}
