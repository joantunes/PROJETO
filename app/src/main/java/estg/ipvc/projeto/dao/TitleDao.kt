package estg.ipvc.projeto.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import estg.ipvc.projeto.entities.Title

@Dao
interface TitleDao {
    @Query("SELECT * from title_table ORDER BY title ASC")
    fun getAlphabetizedTitles(): LiveData<List<Title>>

    //@Query("SELECT * from title_table WHERE notes==:notes")
   // fun getTitlesByNotes(title: String): LiveData<notes>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(title: Title)

    @Query("DELETE FROM title_table")
    suspend fun deleteAll()

    @Query("DELETE FROM title_table WHERE title ==:title")
    suspend fun delete(title: Title)
}