package de.florianweinaug.comicscollection.model

import android.arch.persistence.room.Entity
import android.text.TextUtils

@Entity
class Publisher(id: Int, var name: String, var imageUrl: String?, var description: String?,
                var website: String?) : Model(id) {

    val hasDescription: Boolean
        get() = !TextUtils.isEmpty(this.description)

    override fun toString(): String {
        return this.name
    }
}