package de.florianweinaug.comicscollection.ui.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.view.*
import de.florianweinaug.comicscollection.R
import de.florianweinaug.comicscollection.model.Comic
import de.florianweinaug.comicscollection.ui.settings.SettingsActivity
import de.florianweinaug.comicscollection.ui.statistics.StatisticsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.comic_list.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: ComicListAdapter

    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_comic_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.menu_add_comic -> {
                    val newFragment = AddComicDialog()
                    newFragment.show(supportFragmentManager, newFragment.tag)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_statistics -> {
                val intent = Intent(this, StatisticsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}