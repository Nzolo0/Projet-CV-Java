package io.takima.demo

import javax.persistence.*

/**
 *
 */
@Entity(name = "experience")
data class Experience(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var exp_id: Long?,
        @Column(name = "exp_title") var exp_title: String?,
        @Column(name = "company_name") var company_name: String?,
        @Column(name = "exp_location") var exp_location: String?,
        @Column(name = "start_date") var start_date: String?,
        @Column(name = "end_date") var end_date: String?,
        @Column(name = "exp_description") var exp_description: String?
) {
    constructor() : this(null,null,null,null,null,null,null)
}
