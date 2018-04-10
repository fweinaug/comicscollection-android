package de.florianweinaug.comicscollection.di

import dagger.Component
import de.florianweinaug.comicscollection.ui.comic.ComicViewModel
import de.florianweinaug.comicscollection.ui.issue.IssueViewModel
import de.florianweinaug.comicscollection.ui.main.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)

    fun inject(comicViewModel: ComicViewModel)

    fun inject(issueViewModel: IssueViewModel)
}