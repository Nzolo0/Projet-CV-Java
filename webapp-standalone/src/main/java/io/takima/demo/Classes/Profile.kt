package io.takima.demo.Classes

import io.takima.demo.*
import org.springframework.stereotype.Component

/**
 *
 */
@Component
class Profile {
    var education: Iterable<Education>? = null
    var experience: Iterable<Experience>? = null
    var hobby: Iterable<Hobby>? = null
    var presentation: Iterable<Presentation>? = null
    var project: Iterable<Project>? = null
    var skills: Iterable<Skill>? = null
    var user: Iterable<User>? = null
}
