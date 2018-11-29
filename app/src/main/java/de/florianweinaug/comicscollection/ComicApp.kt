package de.florianweinaug.comicscollection

import android.app.Application
import de.florianweinaug.comicscollection.di.ApiModule
import de.florianweinaug.comicscollection.di.AppComponent
import de.florianweinaug.comicscollection.di.AppModule
import de.florianweinaug.comicscollection.di.DaggerAppComponent

class ComicApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule(BuildConfig.API_URL))
                .build()

        settings = AppSettings(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var settings: AppSettings
    }
}