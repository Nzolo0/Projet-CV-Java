package io.takima.demo

import javax.persistence.*

/**
 *
 */
@Entity(name = "projects")
data class Projects(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var proj_id: Long?,
        @Column(name = "proj_title") var proj_title: String?,
        @Column(name = "proj_date") var proj_date: String?,
        @Column(name = "proj_description") var proj_description: String?
) {
    constructor() : this(null, null, null, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Projects

        if (proj_id != other.proj_id) return false
        if (proj_title != other.proj_title) return false
        if (proj_date != other.proj_date) return false
        if (proj_description != other.proj_description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = proj_id?.hashCode() ?: 0
        result = 31 * result + (proj_title?.hashCode() ?: 0)
        result = 31 * result + (proj_date?.hashCode() ?: 0)
        result = 31 * result + (proj_description?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Projects(proj_id=$proj_id, proj_title=$proj_title, proj_date=$proj_date, proj_description=$proj_description)"
    }
}
