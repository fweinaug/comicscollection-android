package de.florianweinaug.comicscollection.model

import android.arch.persistence.room.Entity

@Entity
class Profile(id: Int, var name: String) : Model(id) {

    override fun toString(): String {
        return this.name
    }
}