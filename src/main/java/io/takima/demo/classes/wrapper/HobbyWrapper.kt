package io.takima.demo.classes.wrapper

import io.takima.demo.Hobby


class HobbyWrapper {

    private var hobbyList: ArrayList<Hobby?> = ArrayList()

    fun getHobbyList(): ArrayList<Hobby?> {
        return hobbyList
    }

    fun setHobbyList(hobbies: ArrayList<Hobby?>) {
        this.hobbyList = hobbies
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HobbyWrapper

        if (hobbyList != other.hobbyList) return false

        return true
    }

    override fun hashCode(): Int {
        return hobbyList.hashCode()
    }

    override fun toString(): String {
        return "HobbyWrapper(hobbyList=$hobbyList)"
    }


}