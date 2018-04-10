package de.florianweinaug.comicscollection.ui.main

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.florianweinaug.comicscollection.databinding.ComicListContentBinding
import de.florianweinaug.comicscollection.model.Comic
import de.florianweinaug.comicscollection.ui.comic.ComicDetailActivity
import de.florianweinaug.comicscollection.ui.comic.ComicDetailFragment
import de.florianweinaug.comicscollection.ui.common.BindingViewHolder

class ComicListAdapter(private val mTwoPane: Boolean) : RecyclerView.Adapter<BindingViewHolder>() {

    private val mItems: ArrayList<Comic> = ArrayList()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Comic
            if (mTwoPane) {
                // TODO: Not yet supported
                /*
                val fragment = ComicDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ComicDetailFragment.ARG_ITEM_ID, item.id.toString())
                    }
                }
                mParentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.comic_detail_container, fragment)
                        .commit()
                */
            } else {
                val intent = Intent(v.context, ComicDetailActivity::class.java).apply {
                    putExtra(ComicDetailFragment.ARG_COMIC_ID, item.id)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ComicListContentBinding.inflate(layoutInflater, parent, false)

        return BindingViewHolder(binding)
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

    fun update(comics: List<Comic>) {
        mItems.clear()
        mItems.addAll(comics)

        notifyDataSetChanged()
    }
}