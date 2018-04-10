package de.florianweinaug.comicscollection.model

import android.arch.persistence.room.Entity
import android.text.TextUtils

@Entity
class Issue(id: Int, var comicId: Int, var number: Short, var title: String?, var summary: String?,
            var imageUrl: String, var read: Boolean, var rating: Byte) : Model(id) {

    val hasTitle: Boolean
        get() = !TextUtils.isEmpty(this.title)

    val hasSummary: Boolean
        get() = !TextUtils.isEmpty(this.summary)
}