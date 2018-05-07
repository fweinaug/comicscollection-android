package de.florianweinaug.comicscollection.ui.statistics

import android.annotation.SuppressLint
import android.arch.lifecycle.*
import android.os.AsyncTask
import de.florianweinaug.comicscollection.ComicApp
import de.florianweinaug.comicscollection.model.Statistics
import de.florianweinaug.comicscollection.repo.ComicRepository
import javax.inject.Inject

class StatisticsViewModel : ViewModel() {

    private lateinit var repository: ComicRepository

    private val statistics: MutableLiveData<Statistics> = MutableLiveData()

    @Inject
    fun init(repository: ComicRepository) {
        this.repository = repository
    }

    fun getStatistics(): LiveData<Statistics> {
        return statistics
    }

    fun refresh() {
        RefreshAsyncTask().execute()
    }

    @SuppressLint("StaticFieldLeak")
    inner class RefreshAsyncTask : AsyncTask<Void, Void, Statistics>() {

        override fun doInBackground(vararg params: Void?): Statistics {
            return repository.getStatistics()
        }

        override fun onPostExecute(result: Statistics) {
            statistics.value = result
        }
    }

    companion object {
        fun create(activity: StatisticsActivity) : StatisticsViewModel {
            val viewModel = ViewModelProviders.of(activity).get(StatisticsViewModel::class.java)
            ComicApp.appComponent.inject(viewModel)

            return viewModel
        }
    }
}