package estg.ipvc.projeto.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import estg.ipvc.projeto.entities.Title

@Dao
interface TitleDao {

    @Query("SELECT * from title_table ORDER BY title ASC")
    fun getAllTitles(): LiveData<List<Title>>

    @Query("DELETE  FROM title_table WHERE title==:title")
    suspend fun deleteByTitle(title: String)

    //@Query("SELECT * from title_table WHERE notes==:notes")
   // fun getTitlesByNotes(title: String): LiveData<notes>

    @Update
    suspend fun updateTitle(title: Title)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(title: Title)

    @Query("DELETE FROM title_table")
    suspend fun deleteAll()

    @Delete
    fun delete(title: Title?)

    @Update
    fun update(title: Title)



    // @Query("DELETE FROM title_table WHERE title ==:title")
    //suspend fun delete(title: Title)
}