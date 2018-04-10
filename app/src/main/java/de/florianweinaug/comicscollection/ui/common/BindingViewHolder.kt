package de.florianweinaug.comicscollection.ui.common

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import de.florianweinaug.comicscollection.BR

class BindingViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Any) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}