package io.takima.demo.Classes

import io.takima.demo.Skill


class SkillWrapper {

    private var skillList: ArrayList<Skill?> = ArrayList()

    fun getSkillList(): ArrayList<Skill?> {
        return skillList
    }

    fun setSkillList(Skills: ArrayList<Skill?>) {
        this.skillList = Skills
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SkillWrapper

        if (skillList != other.skillList) return false

        return true
    }

    override fun hashCode(): Int {
        return skillList.hashCode()
    }

    override fun toString(): String {
        return "SkillWrapper(SkillList=$skillList)"
    }


}