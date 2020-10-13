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
        @Column(name = "edu_location") var edu_location: String?,
        @Column(name = "start_date") var start_date: String?,
        @Column(name = "end_date") var end_date: String?,
        @Column(name = "edu_description") var edu_description: String?
) {
    constructor() : this(null, null, null, null,null,null,null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Education

        if (ed_id != other.ed_id) return false
        if (edu_title != other.edu_title) return false
        if (edu_name != other.edu_name) return false
        if (edu_location != other.edu_location) return false
        if (start_date != other.start_date) return false
        if (end_date != other.end_date) return false
        if (edu_description != other.edu_description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ed_id?.hashCode() ?: 0
        result = 31 * result + (edu_title?.hashCode() ?: 0)
        result = 31 * result + (edu_name?.hashCode() ?: 0)
        result = 31 * result + (edu_location?.hashCode() ?: 0)
        result = 31 * result + (start_date?.hashCode() ?: 0)
        result = 31 * result + (end_date?.hashCode() ?: 0)
        result = 31 * result + (edu_description?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Education(ed_id=$ed_id, edu_title=$edu_title, edu_name=$edu_name, edu_location=$edu_location, start_date=$start_date, end_date=$end_date, edu_description=$edu_description)"
    }


}
