package estg.ipvc.projeto.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import estg.ipvc.projeto.dao.TitleDao
import estg.ipvc.projeto.entities.Title
import kotlinx.coroutines.CoroutineScope

class TitleDB {
    // Annotates class to be a Room Database with a table (entity) of the Word class
    @Database(entities = arrayOf(Title::class), version = 4, exportSchema = false)
    public abstract class TitleDB : RoomDatabase() {

        abstract fun TitleDao(): TitleDao.TitleDao

        companion object {
            // Singleton prevents multiple instances of database opening at the
            // same time.
            @Volatile
            private var INSTANCE: TitleDB? = null

            fun getDatabase(context: Context, scope: CoroutineScope): TitleDB {
                val tempInstance = INSTANCE
                if (tempInstance != null) {
                    return tempInstance
                }
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TitleDB::class.java,
                        "title_database"
                    )
                        .build()

                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}