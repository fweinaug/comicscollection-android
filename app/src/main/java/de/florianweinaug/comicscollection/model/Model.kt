package de.florianweinaug.comicscollection.model

import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

abstract class Model(@PrimaryKey var id: Int) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        val that = other as Model

        return this.id == that.id
    }

    override fun hashCode(): Int {
        return this.id.hashCode()
    }
}