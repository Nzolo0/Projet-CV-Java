package io.takima.demo.Classes

import io.takima.demo.*
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.springframework.stereotype.Component
import java.util.*

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

    public fun getHobbyHTML(): Iterable<Hobby>? {
        val hobbyHTML = ArrayList<Hobby>()
        for ((id, title, details) in this.hobby!!) {
            hobbyHTML.add(Hobby(id, title?.let { markdownToHTML(it) }, details?.let { markdownToHTML(it) }))
        }
        return hobbyHTML
}

    public fun getEducationHTML(): Iterable<Education>? {
        val educationHTML = ArrayList<Education>()
        for ((id, title, name, location, startDate, endDate, description) in this.education!!) {
            educationHTML.add(Education(
                    id,
                    title?.let { markdownToHTML(it) },
                    name?.let { markdownToHTML(it) },
                    location?.let { markdownToHTML(it) },
                    startDate?.let { markdownToHTML(it) },
                    endDate?.let { markdownToHTML(it) },
                    description?.let { markdownToHTML(it) }
            ))
        }
        educationHTML.sort()
        return educationHTML
    }

    public fun getExperienceHTML(): Iterable<Experience>? {
        val experienceHTML = ArrayList<Experience>()
        for ((id, title, companyName, location, startDate, endDate, description) in this.experience!!) {
            experienceHTML.add(
                    Experience(
                            id,
                            title?.let { markdownToHTML(it) },
                            companyName?.let { markdownToHTML(it) },
                            location?.let { markdownToHTML(it) },
                            startDate?.let { markdownToHTML(it) },
                            endDate?.let { markdownToHTML(it) },
                            description?.let { markdownToHTML(it) }

                    ))
        }
        experienceHTML.sort();
        return experienceHTML
    }

    public fun getProjectHTML(): Iterable<Project>? {
        val projectHTML = ArrayList<Project>()
        for ((id, title, date, description) in this.project!!) {
            projectHTML.add(Project(id,
                    title?.let { markdownToHTML(it) },
                    date?.let { markdownToHTML(it) },
                    description?.let { markdownToHTML(it) }))
        }
        projectHTML.sort()
        return projectHTML
    }

    public fun getSkillHTML(): Iterable<Skill>? {
        val skillHTML = ArrayList<Skill>()
        for ((id, name, grade) in this.skills!!) {
            skillHTML.add(Skill(id,
                    name?.let { markdownToHTML(it) },
                    grade?.let { markdownToHTML(it) }))
        }
        return skillHTML
    }


    fun getCurrentUserHTML(): User? {
        var user2 = this.user?.iterator()?.next();
        user2?.id = user2?.id
        user2?.lastName = markdownToHTML(user2?.lastName!!)
        user2.email = markdownToHTML(user2.email!!)
        user2.address = markdownToHTML(user2.address!!)
        return user2
    }
    fun getPresentationHTML(): Presentation? {
        var presentation2 = this.presentation?.iterator()?.next();
        presentation2?.id = presentation2?.id
        presentation2?.title = markdownToHTML(presentation2?.title!!)
        presentation2.description = markdownToHTML(presentation2.description!!)
        return presentation2
    }




    public fun markdownToHTML(markdown: String): String? {
        val parser = Parser.builder()
                .build()
        val document = parser.parse(markdown)
        val renderer = HtmlRenderer.builder()
                .build()
        return renderer.render(document).replace("<p>", "").replace("</p>", "")
    }



}

