package io.takima.demo

import javax.persistence.*

/**
 *
 */
@Entity(name = "users")
data class User(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id var id: Long?,
        @Column(name = "first_name") var firstName: String?,
        @Column(name = "last_name") var lastName: String?,
        @Column(name = "age") var age: Int?,
        @Column(name = "phone") var phone: String?,
        @Column(name = "email") var email: String?,
        @Column(name = "address") var address: String?,
        @Column(name = "title") var title: String?,
        @Column(name = "linkedin") var linkedin: String?,
        @Column(name = "github") var github: String?,
        @Column(name = "facebook") var facebook: String?
) {
    constructor() : this(null, null, null, null,null,null,null,null,null,null,null)

    override fun toString(): String {
        return "User(id=$id, firstName=$firstName, lastName=$lastName, age=$age, phone=$phone, email=$email, address=$address, title=$title, linkedin=$linkedin, github=$github, facebook=$facebook)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (age != other.age) return false
        if (phone != other.phone) return false
        if (email != other.email) return false
        if (address != other.address) return false
        if (title != other.title) return false
        if (linkedin != other.linkedin) return false
        if (github != other.github) return false
        if (facebook != other.facebook) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (firstName?.hashCode() ?: 0)
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + (age ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (linkedin?.hashCode() ?: 0)
        result = 31 * result + (github?.hashCode() ?: 0)
        result = 31 * result + (facebook?.hashCode() ?: 0)
        return result
    }

}
