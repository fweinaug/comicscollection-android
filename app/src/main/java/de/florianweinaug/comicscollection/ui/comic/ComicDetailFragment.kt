package de.florianweinaug.comicscollection.ui.comic

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.florianweinaug.comicscollection.R
import de.florianweinaug.comicscollection.model.Comic
import de.florianweinaug.comicscollection.model.Issue
import kotlinx.android.synthetic.main.activity_comic_detail.*
import kotlinx.android.synthetic.main.fragment_comic_detail.view.*

class ComicDetailFragment : Fragment() {

    private lateinit var mViewModel: ComicViewModel
    private lateinit var mAdapter: IssueListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter = IssueListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_comic_detail, container, false)

        val recyclerView = rootView.issue_list

        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL))

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        mViewModel = ComicViewModel.create(activity as ComicDetailActivity)

        mViewModel.getComic()
                .observe(this, Observer<Comic> { activity?.toolbar_layout?.title = it?.name })

        mViewModel.getIssues()
                .observe(this, Observer<List<Issue>> { if (it != null) mAdapter.update(it) })

        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        const val ARG_COMIC_ID = "comic_id"
    }
}