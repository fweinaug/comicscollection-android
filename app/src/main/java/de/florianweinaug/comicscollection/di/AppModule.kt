package de.florianweinaug.comicscollection.di

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import de.florianweinaug.comicscollection.ComicApp
import de.florianweinaug.comicscollection.db.AppDatabase
import de.florianweinaug.comicscollection.db.ComicDao
import de.florianweinaug.comicscollection.db.IssueDao
import de.florianweinaug.comicscollection.db.PublisherDao
import javax.inject.Singleton

@Module
class AppModule(private val comicApp: ComicApp) {
    @Provides
    @Singleton
    fun provideDatabase() : AppDatabase {
        return Room.databaseBuilder(comicApp, AppDatabase::class.java, "app.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideComicDao(db: AppDatabase) : ComicDao {
        return db.comicDao()
    }

    @Provides
    @Singleton
    fun provideIssueDao(db: AppDatabase) : IssueDao {
        return db.issueDao()
    }

    @Provides
    @Singleton
    fun providePublisherDao(db: AppDatabase) : PublisherDao {
        return db.publisherDao()
    }
}