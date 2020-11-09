package estg.ipvc.projeto.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import estg.ipvc.projeto.db.TitleDB
import estg.ipvc.projeto.db.TitleRepository
import estg.ipvc.projeto.entities.Title
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TitleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TitleRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTitles: LiveData<List<Title>>

    init {
        val titlesDao = TitleDB.getDatabase(application, viewModelScope).titleDao()
        repository = TitleRepository(titlesDao)
        allTitles = repository.allTitles
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(title: Title) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(title)
    }
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO)
    {
        repository.deleteAll()
    }
    fun delete(title: Title)  = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(title) }

    fun update(title: Title)  = viewModelScope.launch(Dispatchers.IO) {

        repository.update(title) }


    /*  fun deleteByTitle(title: EditText) = viewModelScope.launch(Dispatchers.IO)
      {
          repository.deleteByTitle(title)
      }

     */
}