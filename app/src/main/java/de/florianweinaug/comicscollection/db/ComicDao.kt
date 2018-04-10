package de.florianweinaug.comicscollection.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import de.florianweinaug.comicscollection.model.Comic

@Dao
abstract class ComicDao {

    @Query("select * from comic order by name, id")
    abstract fun getComics() : LiveData<List<Comic>>

    @Query("select * from comic where id=:id")
    abstract fun getComicById(id : Int) : LiveData<Comic>

    @Query("delete from comic")
    abstract fun deleteAll()

    @Insert
    abstract fun insert(comic: Comic)

    @Insert
    abstract fun insert(comics: List<Comic>)

    @Update
    abstract fun update(comic: Comic)
}

