package io.takima.demo.classes.file

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity(name = "files")
data class FileDB(
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid2")
        var id: String?,
        @Column(name = "name") var name: String,
        @Column(name = "type") var type: String,
        @Lob
        @Column(name = "data") var data: ByteArray
    ) {

    constructor() : this("", "", "", "".toByteArray())


    constructor(name: String, type: String, data: ByteArray) : this() {
        this.name = name
        this.type = type
        this.data = data
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileDB

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "FileDB(id=$id, name='$name', type='$type', data=${data.contentToString()})"
    }


}