package io.takima.demo

import javax.persistence.*

/**
 *
 */
@Entity(name = "skills")
data class Skill(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var skill_id: Long?,
        @Column(name = "skill_name") var skill_name: String?,
        @Column(name = "skill_grade") var skill_grade: String?
) {
    constructor() : this(null, null, null)

    override fun toString(): String {
        return "Skill(skill_id=$skill_id, skill_name=$skill_name, skill_grade=$skill_grade)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Skill

        if (skill_id != other.skill_id) return false
        if (skill_name != other.skill_name) return false
        if (skill_grade != other.skill_grade) return false

        return true
    }

    override fun hashCode(): Int {
        var result = skill_id?.hashCode() ?: 0
        result = 31 * result + (skill_name?.hashCode() ?: 0)
        result = 31 * result + (skill_grade?.hashCode() ?: 0)
        return result
    }
}
