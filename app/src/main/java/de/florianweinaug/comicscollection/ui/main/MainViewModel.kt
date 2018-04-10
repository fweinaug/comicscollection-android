package de.florianweinaug.comicscollection.ui.main

import android.annotation.SuppressLint
import android.arch.lifecycle.*
import android.os.AsyncTask
import de.florianweinaug.comicscollection.BuildConfig
import de.florianweinaug.comicscollection.ComicApp
import de.florianweinaug.comicscollection.model.Comic
import de.florianweinaug.comicscollection.model.Publisher
import de.florianweinaug.comicscollection.repo.ComicRepository
import javax.inject.Inject

class MainViewModel : ViewModel() {

    private lateinit var repository: ComicRepository

    @Inject
    fun init(repository: ComicRepository) {
        this.repository = repository
    }

    fun refresh() {
        this.repository.refresh(BuildConfig.PROFILE_ID)
    }

    fun getComics(): LiveData<List<Comic>> {
        return this.repository.getComics()
    }

    fun getPublishers() : LiveData<List<Publisher>> {
        return this.repository.getPublishers()
    }

    fun addComic(name: String, publisher: Publisher, concluded: Boolean) {
        AddComicAsyncTask(name, publisher, concluded).execute()
    }

    @SuppressLint("StaticFieldLeak")
    inner class AddComicAsyncTask(private val name: String,
                                  private val publisher: Publisher,
                                  private val concluded: Boolean) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {
            repository.addComic(name, publisher, concluded)

            return null
        }
    }

    companion object {
        fun create(activity: MainActivity) : MainViewModel {
            val viewModel = ViewModelProviders.of(activity).get(MainViewModel::class.java)
            ComicApp.appComponent.inject(viewModel)

            return viewModel
        }
    }
}