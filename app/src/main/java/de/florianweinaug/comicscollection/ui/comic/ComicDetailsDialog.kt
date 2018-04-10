package de.florianweinaug.comicscollection.ui.comic

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.florianweinaug.comicscollection.databinding.DialogComicDetailsBinding
import de.florianweinaug.comicscollection.model.Comic

class ComicDetailsDialog : BottomSheetDialogFragment() {

    private lateinit var mBinding: DialogComicDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DialogComicDetailsBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val viewModel = ComicViewModel.create(activity as ComicDetailActivity)

        viewModel.getComic()
                .observe(this, Observer<Comic> { mBinding.item = it })

        super.onActivityCreated(savedInstanceState)
    }
}