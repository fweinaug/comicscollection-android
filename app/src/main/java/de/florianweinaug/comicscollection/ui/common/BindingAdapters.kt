package de.florianweinaug.comicscollection.ui.common

import android.databinding.BindingAdapter
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import de.florianweinaug.comicscollection.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
     fun setImageUrl(view: ImageView, url: String?) {
        if (TextUtils.isEmpty(url)) {
            Glide.with(view.context)
                    .asDrawable()
                    .load(R.drawable.placeholder)
                    .into(view)
        } else {
            val options = RequestOptions
                    .placeholderOf(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)

            Glide.with(view.context)
                    .load(url)
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, value: Boolean?) {
        view.visibility = if (value == true) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setText(view: TextView, value: Boolean) {
        view.setText(if (value) R.string.yes else R.string.no)
    }
}