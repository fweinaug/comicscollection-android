package de.florianweinaug.comicscollection.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import de.florianweinaug.comicscollection.model.Issue

@Dao
abstract class IssueDao {

    @Query("select * from issue where comicId=:id order by number")
    abstract fun getIssuesByComicId(id: Int): LiveData<List<Issue>>

    @Query("select * from issue where id=:id")
    abstract fun getIssueById(id: Int) : LiveData<Issue>

    @Query("delete from issue")
    abstract fun deleteAll()

    @Query("select count(*) from issue")
    abstract fun countAll() : Int

    @Query("select count(*) from issue where read = 1")
    abstract fun countRead() : Int

    @Insert
    abstract fun insert(issue: Issue)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(issues: List<Issue>)

    @Update
    abstract fun update(issue: Issue)

    @Update
    abstract fun update(issues: List<Issue>)
}