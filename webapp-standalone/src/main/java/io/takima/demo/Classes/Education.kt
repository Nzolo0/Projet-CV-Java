package io.takima.demo

import javax.persistence.*

/**
 *
 */
@Entity(name = "educations")
data class Education(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var id: Long?,
        @Column(name = "title") var title: String?,
        @Column(name = "name") var name: String?,
        @Column(name = "location") var location: String?,
        @Column(name = "start_date") var startDate: String?,
        @Column(name = "end_date") var endDate: String?,
        @Column(name = "description") var description: String?
) : Comparable<Education> {
    constructor() : this(null, null, null, null, null, null, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Education

        if (id != other.id) return false
        if (title != other.title) return false
        if (name != other.name) return false
        if (location != other.location) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (location?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Education(ed_id=$id, edu_title=$title, edu_name=$name, edu_location=$location, start_date=$startDate, end_date=$endDate, edu_description=$description)"
    }

    override fun compareTo(other: Education): Int {

        if (endDate.isNullOrEmpty() && other.endDate.isNullOrEmpty())
            return 0
        if (endDate.isNullOrEmpty()) return -1
        if (other.endDate.isNullOrEmpty()) return 1
        return other.endDate!!.compareTo(endDate!!)

    }

}
