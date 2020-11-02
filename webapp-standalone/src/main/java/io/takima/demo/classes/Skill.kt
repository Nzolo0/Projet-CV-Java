package io.takima.demo

import javax.persistence.*

/**
 * Information about Skills
 */
@Entity(name = "skills")
data class Skill(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var id: Long?,
        @Column(name = "name") var name: String?,
        @Column(name = "grade") var grade: String?
) : Comparable<Skill> {
    constructor() : this(null, null, null)

    override fun toString(): String {
        return "Skill(id=$id, name=$name, grade=$grade)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Skill

        if (id != other.id) return false
        if (name != other.name) return false
        if (grade != other.grade) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (grade?.hashCode() ?: 0)
        return result
    }
    /**
     * Sort skills
     */
    override fun compareTo(other: Skill): Int {

        if (name.isNullOrEmpty() && other.name.isNullOrEmpty())
            return 0
        if (name.isNullOrEmpty()) return -1
        if (other.name.isNullOrEmpty()) return 1
        return name!!.compareTo(other.name!!)

    }
}
