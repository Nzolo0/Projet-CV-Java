package io.takima.demo.classes.wrapper

import io.takima.demo.Project


class ProjectWrapper {

    private var projectList: ArrayList<Project?> = ArrayList()

    fun getProjectList(): ArrayList<Project?> {
        return projectList
    }

    fun setProjectList(projects: ArrayList<Project?>) {
        this.projectList = projects
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProjectWrapper

        if (projectList != other.projectList) return false

        return true
    }

    override fun hashCode(): Int {
        return projectList.hashCode()
    }

    override fun toString(): String {
        return "ProjectWrapper(projectList=$projectList)"
    }


}