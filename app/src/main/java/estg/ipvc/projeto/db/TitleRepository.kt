package estg.ipvc.projeto.db

import androidx.lifecycle.LiveData
import estg.ipvc.projeto.dao.TitleDao
import estg.ipvc.projeto.entities.Title


class TitleRepository(private val titleDao: TitleDao.TitleDao) {
    // Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO


        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        val allTitles: LiveData<List<Title>> = titleDao.getAlphabetizedTitles()






        suspend fun insert(title: Title) {
            titleDao.insert(title)
        }

}