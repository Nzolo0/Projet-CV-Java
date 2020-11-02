package io.takima.demo.classes.wrapper

import io.takima.demo.Education


class EducationWrapper {

    private var educationList: ArrayList<Education?> = ArrayList()

    fun getEducationList(): ArrayList<Education?> {
        return educationList
    }

    fun setEducationList(educations: ArrayList<Education?>) {
        this.educationList = educations
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EducationWrapper

        if (educationList != other.educationList) return false

        return true
    }

    override fun hashCode(): Int {
        return educationList.hashCode()
    }

    override fun toString(): String {
        return "EducationWrapper(educationList=$educationList)"
    }


}