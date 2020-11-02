package io.takima.demo

/**
 * Information about LinkedinData
 */

class LinkedinData {

    var localizedLastName: String? = null
    var profilePicture: ProfilePicture? = null
    var firstName: FirstName? = null
    var lastName: LastName? = null
    var id: String? = null
    var localizedFirstName: String? = null

}

class ProfilePicture {
    var displayImage: String? = null
}

class Localized {
    var en_US: String? = null
    var fr_FR: String? = null
}

class FirstName {
    var localized: Localized? = null
    var preferredLocale: PreferredLocale? = null
}

class PreferredLocale {
    var country: String? = null
    var language: String? = null
}

class LastName {
    var localized: Localized? = null
    var preferredLocale: PreferredLocale? = null
}