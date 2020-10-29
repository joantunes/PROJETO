package estg.ipvc.projeto.db

import androidx.lifecycle.LiveData
import estg.ipvc.projeto.dao.TitleDao
import estg.ipvc.projeto.entities.Title


class TitleRepository(private val titleDao: TitleDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTitles: LiveData<List<Title>> = titleDao.getAlphabetizedTitles()

    suspend fun insert(title: Title) {
        titleDao.insert(title)

    }
}