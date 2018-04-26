package de.florianweinaug.comicscollection.ui.main

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import de.florianweinaug.comicscollection.ComicApp
import de.florianweinaug.comicscollection.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ComicAsyncTask().execute()
    }

    @SuppressLint("StaticFieldLeak")
    inner class ComicAsyncTask : AsyncTask<Void, Void, Boolean>()
    {
        override fun doInBackground(vararg params: Void?): Boolean {
            val viewModel = ViewModelProviders
                    .of(this@SplashActivity)
                    .get(MainViewModel::class.java)
            ComicApp.appComponent.inject(viewModel)

            viewModel.refresh()

            return true
        }

        override fun onPostExecute(success: Boolean)
        {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
}