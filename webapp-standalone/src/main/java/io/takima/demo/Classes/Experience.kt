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

    override fun toString(): String {
        return "Experience(exp_id=$exp_id, exp_title=$exp_title, company_name=$company_name, exp_location=$exp_location, start_date=$start_date, end_date=$end_date, exp_description=$exp_description)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Experience

        if (exp_id != other.exp_id) return false
        if (exp_title != other.exp_title) return false
        if (company_name != other.company_name) return false
        if (exp_location != other.exp_location) return false
        if (start_date != other.start_date) return false
        if (end_date != other.end_date) return false
        if (exp_description != other.exp_description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = exp_id?.hashCode() ?: 0
        result = 31 * result + (exp_title?.hashCode() ?: 0)
        result = 31 * result + (company_name?.hashCode() ?: 0)
        result = 31 * result + (exp_location?.hashCode() ?: 0)
        result = 31 * result + (start_date?.hashCode() ?: 0)
        result = 31 * result + (end_date?.hashCode() ?: 0)
        result = 31 * result + (exp_description?.hashCode() ?: 0)
        return result
    }
}
