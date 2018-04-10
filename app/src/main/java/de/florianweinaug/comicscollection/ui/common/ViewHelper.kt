package de.florianweinaug.comicscollection.ui.common

import android.content.res.ColorStateList
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import de.florianweinaug.comicscollection.R

object ViewHelper {

    @JvmStatic
    fun setRead(fab: FloatingActionButton, value: Boolean) {
        if (value) {
            fab.setImageResource(R.drawable.ic_fab_read)
            fab.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(fab.context, R.color.colorSuccess))
        } else {
            fab.setImageResource(R.drawable.ic_fab_unread)
            fab.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(fab.context, R.color.colorAccent))
        }
    }
}