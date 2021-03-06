package de.florianweinaug.comicscollection.ui.comic

import android.annotation.SuppressLint
import android.arch.lifecycle.*
import android.os.AsyncTask
import de.florianweinaug.comicscollection.ComicApp
import de.florianweinaug.comicscollection.model.Comic
import de.florianweinaug.comicscollection.model.Issue
import de.florianweinaug.comicscollection.repo.ComicRepository
import java.io.File
import javax.inject.Inject

class ComicViewModel : ViewModel() {

    private lateinit var repository: ComicRepository

    private val comicId: MutableLiveData<Int> = MutableLiveData()
    private val comic: LiveData<Comic>
    private val issues: LiveData<List<Issue>>
    private val read: LiveData<Boolean>
    private val issuesView: MutableLiveData<IssuesView> = MutableLiveData()

    init {
        comic = Transformations.switchMap(comicId) {
            repository.getComic(it)
        }

        issues = Transformations.switchMap(comicId) {
            repository.getIssues(it)
        }

        read = Transformations.map(comic) {
            it.read
        }

        setView(ComicApp.settings.issuesView)
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

    fun addIssue(imageFile: File) {
        AddIssueAsyncTask(comic.value!!, imageFile).execute()
    }

    fun markAsRead() {
        MarkAsReadAsyncTask(comic.value!!).execute()
    }

    fun getView(): MutableLiveData<IssuesView> {
        return issuesView
    }

    fun setView(view: IssuesView) {
        if (issuesView.value != view) {
            issuesView.value = view

            ComicApp.settings.issuesView = view
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class AddIssueAsyncTask(private val comic: Comic,
                                  private val imageFile: File) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {
            repository.addIssue(comic, imageFile)

            return null
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class MarkAsReadAsyncTask(private val comic: Comic) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {
            val read = !comic.read

            repository.setRead(comic, null, read)

            return null
        }
    }

    companion object {
        fun create(activity: ComicDetailActivity) : ComicViewModel {
            val viewModel = ViewModelProviders.of(activity).get(ComicViewModel::class.java)
            ComicApp.appComponent.inject(viewModel)

            return viewModel
        }
    }
}