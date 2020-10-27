package estg.ipvc.projeto.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import estg.ipvc.projeto.entities.Title


@Dao
interface TitleDao {

    interface TitleDao {

        @Query("SELECT * from title_table ORDER BY title ASC")
        fun getAlphabetizedWords(): LiveData<List<Title>>


        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(word: Title)

        @Query("DELETE FROM title_table")
        suspend fun deleteAll()
    }
}