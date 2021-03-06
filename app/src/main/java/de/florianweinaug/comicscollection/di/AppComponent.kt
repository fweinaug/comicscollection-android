package de.florianweinaug.comicscollection.di

import dagger.Component
import de.florianweinaug.comicscollection.ui.comic.ComicViewModel
import de.florianweinaug.comicscollection.ui.issue.IssueViewModel
import de.florianweinaug.comicscollection.ui.issue.IssuesViewModel
import de.florianweinaug.comicscollection.ui.main.MainViewModel
import de.florianweinaug.comicscollection.ui.statistics.StatisticsViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)

    fun inject(comicViewModel: ComicViewModel)

    fun inject(issuesViewModel: IssuesViewModel)

    fun inject(issueViewModel: IssueViewModel)

    fun inject(statisticsViewModel: StatisticsViewModel)
}