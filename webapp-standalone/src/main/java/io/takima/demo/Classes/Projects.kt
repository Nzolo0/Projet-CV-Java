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
}
