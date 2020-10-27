package estg.ipvc.projeto.db

import android.content.Context
import android.provider.SyncStateContract.Helpers.insert
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import estg.ipvc.projeto.dao.TitleDao
import estg.ipvc.projeto.entities.Title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TitleDB {
    // Annotates class to be a Room Database with a table (entity) of the Word class
    @Database(entities = arrayOf(Title::class), version = 4, exportSchema = false)
    public abstract class TitleDB : RoomDatabase() {

        abstract fun TitleDao(): TitleDao


        private class WordDataBaseCallBack(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {


            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        var titleDao = database.()


                        titleDao.deleteAll()

                        // Add sample words.
                        var title = Title(1, "Viana do Castelo", "PASSOU?", "12-02-00")
                        titleDao.insert(title)
                        title = Title(2, "Porto", "Semaforo", "12-05-00")
                        titleDao.insert(title)


                    }
                }
            }
        }


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
                        .addCallback(WordDataBaseCallBack(scope))
                        .build()

                    INSTANCE = instance
                    return instance
                }

            }
        }
    }
}