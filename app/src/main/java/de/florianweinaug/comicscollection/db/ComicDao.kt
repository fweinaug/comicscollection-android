package de.florianweinaug.comicscollection.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import de.florianweinaug.comicscollection.model.Comic
import de.florianweinaug.comicscollection.model.PublisherCount

@Dao
abstract class ComicDao {

    @Query("select * from comic order by name, id")
    abstract fun getComics() : LiveData<List<Comic>>

    @Query("select * from comic where id=:id")
    abstract fun getComicById(id : Int) : LiveData<Comic>

    @Query("delete from comic")
    abstract fun deleteAll()

    @Query("select count(*) from comic")
    abstract fun countAll() : Int

    @Query("select publisher_name as name, count(*) as count from comic group by publisher_name")
    abstract fun countAllByPublisher() : List<PublisherCount>

    @Query("select count(*) from comic where concluded = 1")
    abstract fun countConcluded() : Int

    @Query("select count(*) from comic where issuesTotal > 0 and issuesTotal = issuesRead")
    abstract fun countRead() : Int

    @Insert
    abstract fun insert(comic: Comic)

    @Insert
    abstract fun insert(comics: List<Comic>)

    @Update
    abstract fun update(comic: Comic)
}