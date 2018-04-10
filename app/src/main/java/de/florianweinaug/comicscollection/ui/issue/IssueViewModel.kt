package de.florianweinaug.comicscollection.ui.issue

import android.annotation.SuppressLint
import android.arch.lifecycle.*
import android.os.AsyncTask
import de.florianweinaug.comicscollection.ComicApp
import de.florianweinaug.comicscollection.model.Comic
import de.florianweinaug.comicscollection.model.Issue
import de.florianweinaug.comicscollection.repo.ComicRepository
import javax.inject.Inject

class IssueViewModel : ViewModel() {

    private lateinit var repository: ComicRepository

    private val comicId: MutableLiveData<Int> = MutableLiveData()
    private val comic: LiveData<Comic>
    private val issues: LiveData<List<Issue>>
    private val issue: MutableLiveData<Issue> = MutableLiveData()
    private val read: MutableLiveData<Boolean> = MutableLiveData()

    init {
        comic = Transformations.switchMap(comicId) {
            repository.getComic(it)
        }

        issues = Transformations.switchMap(comicId) {
            repository.getIssues(it)
        }
    }

    @Inject
    fun init(repository: ComicRepository) {
        this.repository = repository
    }

    fun setId(id: Int) {
        comicId.value = id
    }

    fun getComic() : LiveData<Comic> {
        return comic
    }

    fun getIssues() : LiveData<List<Issue>> {
        return issues
    }

    fun getRead() : LiveData<Boolean> {
        return read
    }

    fun setIssue(issue: Issue) {
        this.issue.value = issue
        this.read.value = issue.read
    }

    fun markAsRead() {
        MarkAsReadAsyncTask(comic.value!!, issue.value!!).execute()
    }

    @SuppressLint("StaticFieldLeak")
    inner class MarkAsReadAsyncTask(private val comic: Comic,
                                    private val issue: Issue) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void?): Boolean {
            val read = !issue.read

            repository.setRead(comic, issue, read)

            return read
        }

        override fun onPostExecute(result: Boolean) {
            read.value = result
        }
    }

    companion object {
        fun create(activity: IssueDetailActivity) : IssueViewModel {
            val viewModel = ViewModelProviders.of(activity).get(IssueViewModel::class.java)
            ComicApp.appComponent.inject(viewModel)

            return viewModel
        }
    }
}