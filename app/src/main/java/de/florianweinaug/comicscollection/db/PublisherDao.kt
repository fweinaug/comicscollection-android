package de.florianweinaug.comicscollection.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import de.florianweinaug.comicscollection.model.Publisher

@Dao
abstract class PublisherDao {
    @Query("select * from publisher order by name")
    abstract fun getPublishers() : LiveData<List<Publisher>>

    @Query("delete from publisher")
    abstract fun deleteAll()

    @Insert
    abstract fun insert(comics: List<Publisher>)
}