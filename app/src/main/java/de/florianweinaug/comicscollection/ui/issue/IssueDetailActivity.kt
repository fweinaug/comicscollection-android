package de.florianweinaug.comicscollection.ui.issue

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.*
import de.florianweinaug.comicscollection.R
import de.florianweinaug.comicscollection.model.Issue
import de.florianweinaug.comicscollection.ui.common.ViewHelper
import kotlinx.android.synthetic.main.activity_issue_detail.*

class IssueDetailActivity : AppCompatActivity() {

    private lateinit var mViewModel: IssueViewModel
    private lateinit var mPagerAdapter: IssuePagerAdapter

    private var mIssueId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_issue_detail)
        setSupportActionBar(issue_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val comicId = intent.getIntExtra(ARG_COMIC_ID, 0)
        val issueId = intent.getIntExtra(ARG_ISSUE_ID, 0)

        mPagerAdapter = IssuePagerAdapter(supportFragmentManager)
        mIssueId = issueId

        container.adapter = mPagerAdapter

        issue_fab.setOnClickListener { _ -> mViewModel.markAsRead() }

        container.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                val issue = mPagerAdapter.getIssue(position)

                mViewModel.setIssue(issue)

                mIssueId = issue.id
            }
        })

        createViewModel(comicId)
    }

    private fun createViewModel(comicId: Int) {
        mViewModel = IssueViewModel.create(this)

        mViewModel.getComic()
                .observe(this, Observer { toolbar_layout?.title = it?.name })

        mViewModel.getIssues()
                .observe(this, Observer { if (it != null) updatePager(it, mIssueId) })

        mViewModel.getRead()
                .observe(this, Observer { ViewHelper.setRead(issue_fab, it ?: false) })

        mViewModel.setId(comicId)
    }

    private fun updatePager(issues: List<Issue>, issueId: Int) {
        mPagerAdapter.update(issues)

        val position = issues.indexOfFirst { issue -> issue.id == issueId }
        container.setCurrentItem(position, false)

        val issue = mPagerAdapter.getIssue(position)
        mViewModel.setIssue(issue)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_issue_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    NavUtils.navigateUpFromSameTask(this)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    companion object {
        const val ARG_COMIC_ID = "comic_id"
        const val ARG_ISSUE_ID = "issue_id"
    }
}