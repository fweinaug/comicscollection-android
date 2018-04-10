package de.florianweinaug.comicscollection.ui.issue

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import de.florianweinaug.comicscollection.model.Issue

class IssuePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mItems : ArrayList<Issue> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return IssueDetailFragment.newInstance(mItems[position])
    }

    override fun getCount(): Int {
        return mItems.size
    }

    fun getIssue(position: Int) : Issue {
        return mItems[position]
    }

    fun update(issues: List<Issue>) {
        mItems.clear()
        mItems.addAll(issues)

        notifyDataSetChanged()
    }
}