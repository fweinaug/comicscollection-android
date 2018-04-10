package de.florianweinaug.comicscollection.ui.comic

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.florianweinaug.comicscollection.databinding.IssueListContentBinding
import de.florianweinaug.comicscollection.model.Issue
import de.florianweinaug.comicscollection.ui.common.BindingViewHolder
import de.florianweinaug.comicscollection.ui.issue.IssueDetailActivity

class IssueListAdapter : RecyclerView.Adapter<BindingViewHolder>() {

    private val mItems: ArrayList<Issue> = ArrayList()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val issue = v.tag as Issue

            val intent = Intent(v.context, IssueDetailActivity::class.java).apply {
                putExtra(IssueDetailActivity.ARG_COMIC_ID, issue.comicId)
                putExtra(IssueDetailActivity.ARG_ISSUE_ID, issue.id)
            }
            v.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = IssueListContentBinding.inflate(layoutInflater, parent, false)

        return BindingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = mItems[position]

        holder.bind(item)

        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun update(issues: List<Issue>) {
        mItems.clear()
        mItems.addAll(issues)

        notifyDataSetChanged()
    }
}