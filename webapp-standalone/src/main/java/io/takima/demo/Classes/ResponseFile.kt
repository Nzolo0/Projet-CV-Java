package io.takima.demo.Classes

class ResponseFile(var name: String, var url: String, var type: String, var size: Long) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResponseFile

        if (name != other.name) return false
        if (url != other.url) return false
        if (type != other.type) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + size.hashCode()
        return result
    }

    override fun toString(): String {
        return "ResponseFile(name='$name', url='$url', type='$type', size=$size)"
    }


}