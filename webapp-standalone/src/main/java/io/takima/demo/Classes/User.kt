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
        @Column(name = "facebook") var facebook: String?,
        @Column(name = "twitter") var twitter: String?
) {
    constructor() : this(null, null, null, null,null,null,null,null,null,null,null,null)

}
