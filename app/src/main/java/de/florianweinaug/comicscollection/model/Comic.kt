package de.florianweinaug.comicscollection.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore

@Entity
class Comic(id: Int = 0, var name: String = "", var issuesTotal: Short = 0,
            var issuesRead: Short = 0, var imageUrl: String? = null,
            @Embedded(prefix = "publisher_") var publisher: Publisher? = null,
            var concluded: Boolean = false, var favorite: Boolean = false,
            @Ignore val issues: List<Issue> = emptyList()) : Model(id) {

    val hasPublisher: Boolean
        get() = this.publisher != null

    val read: Boolean
        get() = this.issuesTotal > 0 && this.issuesTotal == this.issuesRead
}