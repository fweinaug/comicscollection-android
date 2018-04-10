package de.florianweinaug.comicscollection.ui.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.view.*
import de.florianweinaug.comicscollection.R
import de.florianweinaug.comicscollection.model.Comic
import de.florianweinaug.comicscollection.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.comic_list.*

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: ComicListAdapter

    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (comic_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        mAdapter = ComicListAdapter(mTwoPane)

        val recyclerView = comic_list

        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL))

        createViewModel()
    }

    private fun createViewModel() {
        mViewModel = MainViewModel.create(this)

        mViewModel.getComics()
                .observe(this, Observer<List<Comic>> { if (it != null) mAdapter.update(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_comic_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.menu_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_add_comic -> {
                    val newFragment = AddComicDialog()
                    newFragment.show(supportFragmentManager, newFragment.tag)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

}