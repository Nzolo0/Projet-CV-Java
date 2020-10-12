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
}
