package de.florianweinaug.comicscollection

import android.content.Context
import de.florianweinaug.comicscollection.ui.comic.IssuesView

class AppSettings(context: Context) {

    private var preferences = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    var issuesView: IssuesView
        get() {
            val s = preferences.getString("ISSUES_VIEW", defaultIssuesView)
            return IssuesView.valueOf(s)
        }
        set(value) {
            with(preferences.edit()) {
                putString("ISSUES_VIEW", value.toString())
                apply()
            }
        }

    companion object {
        private const val sharedPreferencesName = "de.florianweinaug.comicscollection.settings"

        private var defaultIssuesView = IssuesView.List.toString()
    }
}