package de.florianweinaug.comicscollection.di

import dagger.Module
import dagger.Provides
import de.florianweinaug.comicscollection.api.ComicApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule(private val baseUrl: String) {
    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit) : ComicApi {
        return retrofit.create(ComicApi::class.java)
    }
}