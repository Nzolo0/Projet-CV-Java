package io.takima.demo.classes.wrapper

import io.takima.demo.Experience


class ExperienceWrapper {

    private var experienceList: ArrayList<Experience?> = ArrayList()

    fun getExperienceList(): ArrayList<Experience?> {
        return experienceList
    }

    fun setExperienceList(experiences: ArrayList<Experience?>) {
        this.experienceList = experiences
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExperienceWrapper

        if (experienceList != other.experienceList) return false

        return true
    }

    override fun hashCode(): Int {
        return experienceList.hashCode()
    }

    override fun toString(): String {
        return "ExperienceWrapper(experienceList=$experienceList)"
    }


}