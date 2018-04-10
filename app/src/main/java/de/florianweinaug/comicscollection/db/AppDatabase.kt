package de.florianweinaug.comicscollection.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import de.florianweinaug.comicscollection.model.Comic
import de.florianweinaug.comicscollection.model.Issue
import de.florianweinaug.comicscollection.model.Publisher

@Database(entities = [(Comic::class), (Issue::class), (Publisher::class)], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun comicDao() : ComicDao

    abstract fun issueDao(): IssueDao

    abstract fun publisherDao(): PublisherDao
}