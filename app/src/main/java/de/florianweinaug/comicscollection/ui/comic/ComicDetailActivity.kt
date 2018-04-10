package de.florianweinaug.comicscollection.ui.comic

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.Menu
import android.view.MenuItem
import de.florianweinaug.comicscollection.R
import de.florianweinaug.comicscollection.ui.common.CameraActivity
import de.florianweinaug.comicscollection.ui.common.ViewHelper
import kotlinx.android.synthetic.main.activity_comic_detail.*
import java.io.File

class ComicDetailActivity : CameraActivity() {

    private lateinit var mViewModel: ComicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_comic_detail)
        setSupportActionBar(comic_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragment = ComicDetailFragment()

            supportFragmentManager.beginTransaction()
                    .add(R.id.comic_detail_container, fragment)
                    .commit()
        }

        val comicId = intent.getIntExtra(ComicDetailFragment.ARG_COMIC_ID, 0)

        comic_fab.setOnClickListener {
            mViewModel.markAsRead()
        }

        createViewModel(comicId)
    }

    private fun createViewModel(comicId: Int) {
        mViewModel = ComicViewModel.create(this)

        mViewModel.getRead()
                .observe(this, Observer { ViewHelper.setRead(comic_fab, it ?: false) })

        mViewModel.setId(comicId)
    }

    override fun processPhoto(file: File) {
        mViewModel.addIssue(file)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_comic_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    NavUtils.navigateUpFromSameTask(this)
                    true
                }
                R.id.menu_add_issue -> {
                    dispatchTakePictureIntent()
                    true
                }
                R.id.menu_details -> {
                    val fragment = ComicDetailsDialog()
                    fragment.show(supportFragmentManager, null)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}