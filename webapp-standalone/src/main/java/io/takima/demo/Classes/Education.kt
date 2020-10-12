package io.takima.demo

import javax.persistence.*

/**
 *
 */
@Entity(name = "education")
data class Education(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var ed_id: Long?,
        @Column(name = "edu_title") var edu_title: String?,
        @Column(name = "edu_name") var edu_name: String?,
        @Column(name = "edu_location") var edu_location: Int?,
        @Column(name = "start_date") var start_date: String?,
        @Column(name = "end_date") var end_date: String?,
        @Column(name = "edu_description") var edu_description: String?
) {
    constructor() : this(null, null, null, null,null,null,null)
}
