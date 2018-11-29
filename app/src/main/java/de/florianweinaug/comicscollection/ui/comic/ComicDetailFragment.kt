package de.florianweinaug.comicscollection.ui.comic

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.florianweinaug.comicscollection.R
import kotlinx.android.synthetic.main.activity_comic_detail.*
import kotlinx.android.synthetic.main.fragment_comic_detail.view.*

class ComicDetailFragment : Fragment() {

    private lateinit var mViewModel: ComicViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: IssueListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter = IssueListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_comic_detail, container, false)

        mRecyclerView = rootView.issue_list
        mRecyclerView.adapter = mAdapter

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        mViewModel = ComicViewModel.create(activity as ComicDetailActivity)

        mViewModel.getComic()
                .observe(this, Observer { activity?.toolbar_layout?.title = it?.name })

        mViewModel.getIssues()
                .observe(this, Observer { if (it != null) mAdapter.update(it) })

        mViewModel.getView()
                .observe(this, Observer { if (it != null) changeView(it) })

        super.onActivityCreated(savedInstanceState)
    }

    private fun changeView(view: IssuesView) =
        when (view) {
            IssuesView.List -> {
                mRecyclerView.layoutManager = LinearLayoutManager(activity)

                if (mRecyclerView.itemDecorationCount == 0) {
                    mRecyclerView.addItemDecoration(DividerItemDecoration(
                            context, DividerItemDecoration.VERTICAL))
                }

                true
            }
            IssuesView.Grid -> {
                mRecyclerView.layoutManager = GridLayoutManager(activity, 4)

                if (mRecyclerView.itemDecorationCount > 0) {
                    mRecyclerView.removeItemDecorationAt(0)
                }

                true
        }
    }

    companion object {
        const val ARG_COMIC_ID = "comic_id"
    }
}