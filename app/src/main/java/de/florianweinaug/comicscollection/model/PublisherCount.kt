package de.florianweinaug.comicscollection.model

import android.arch.persistence.room.Entity

@Entity
class PublisherCount(val name: String, val count: Int)