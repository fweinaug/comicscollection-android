package de.florianweinaug.comicscollection.ui.issue

import android.arch.lifecycle.*
import de.florianweinaug.comicscollection.ComicApp
import de.florianweinaug.comicscollection.model.Issue
import de.florianweinaug.comicscollection.repo.ComicRepository
import javax.inject.Inject

class IssueViewModel : ViewModel() {
    private lateinit var repository: ComicRepository

    private val issueId: MutableLiveData<Int> = MutableLiveData()
    private val issue: LiveData<Issue>

    init {
        issue = Transformations.switchMap(issueId) {
            repository.getIssue(it)
        }
    }

    @Inject
    fun init(repository: ComicRepository) {
        this.repository = repository
    }

    fun setId(id: Int) {
        issueId.value = id
    }

    fun getIssue() : LiveData<Issue> {
        return issue
    }

    companion object {
        fun create(fragment: IssueDetailFragment) : IssueViewModel {
            val viewModel = ViewModelProviders.of(fragment).get(IssueViewModel::class.java)
            ComicApp.appComponent.inject(viewModel)

            return viewModel
        }
    }
}