package io.takima.demo

import javax.persistence.*

/**
 * Information about Presentations
 */
@Entity(name = "presentations")
data class Presentation(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var id: Long?,
        @Column(name = "title") var title: String?,
        @Column(name = "description") var description: String?
) {
    constructor() : this(null, null, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Presentation

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Presentation(id=$id, title=$title, description=$description)"
    }
}
