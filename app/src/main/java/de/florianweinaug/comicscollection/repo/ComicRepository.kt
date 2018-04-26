package de.florianweinaug.comicscollection.repo

import android.arch.lifecycle.LiveData
import de.florianweinaug.comicscollection.model.Comic
import javax.inject.Inject
import de.florianweinaug.comicscollection.api.ComicApi
import de.florianweinaug.comicscollection.db.AppDatabase
import de.florianweinaug.comicscollection.db.ComicDao
import de.florianweinaug.comicscollection.db.IssueDao
import de.florianweinaug.comicscollection.db.PublisherDao
import de.florianweinaug.comicscollection.model.Issue
import de.florianweinaug.comicscollection.model.Publisher
import okhttp3.MediaType
import java.io.File
import okhttp3.RequestBody
import okhttp3.MultipartBody

class ComicRepository
@Inject constructor(private val comicApi: ComicApi, private val db: AppDatabase,
                    private val comicDao: ComicDao, private val issueDao: IssueDao,
                    private val publisherDao: PublisherDao) {

    fun getComics() : LiveData<List<Comic>> {
        return comicDao.getComics()
    }

    fun getComic(id: Int) : LiveData<Comic> {
        return comicDao.getComicById(id)
    }

    fun getIssues(id: Int) : LiveData<List<Issue>> {
        return issueDao.getIssuesByComicId(id)
    }

    fun getIssue(id: Int) : LiveData<Issue> {
        return issueDao.getIssueById(id)
    }

    fun getPublishers() : LiveData<List<Publisher>> {
        return publisherDao.getPublishers()
    }

    fun refresh(profileId: Int) {
        ComicRepository.profileId = profileId

        val comics = comicApi.getComics(profileId).execute().body()

        if (comics != null) {
            val publishers = comicApi.getPublishers().execute().body()

            db.runInTransaction({
                issueDao.deleteAll()
                comicDao.deleteAll()

                comicDao.insert(comics)

                comics.forEach {
                    issueDao.insert(it.issues)
                }

                if (publishers != null) {
                    publisherDao.deleteAll()
                    publisherDao.insert(publishers)
                }
            })
        }
    }

    fun addComic(name: String, publisher: Publisher, concluded: Boolean) {
        val comic = comicApi.createComic(profileId, name, publisher.id, concluded).execute().body()

        if (comic != null) {
            comicDao.insert(comic)
        }
    }

    fun addIssue(comic: Comic, imageFile: File) {
        val comicId = RequestBody.create(MediaType.parse("text/plain"), comic.id.toString())

        val imageBody = RequestBody.create(MediaType.parse("image/*"), imageFile)
        val imagePart = MultipartBody.Part.createFormData("upload", imageFile.name, imageBody)

        val updatedComic = comicApi.createIssue(profileId, comicId, imagePart).execute().body()

        if (updatedComic != null) {
            comicDao.update(updatedComic)
            issueDao.insert(updatedComic.issues)
        }
    }

    fun updateIssue(issue: Issue, title: String, summary: String) {
        val updatedIssue = comicApi.updateIssue(profileId, issue.id, title, summary).execute().body()

        if (updatedIssue != null) {
            issueDao.update(updatedIssue)
        }
    }

    fun setRead(comic: Comic, issue: Issue?, read: Boolean) {
        val updatedComic = comicApi.setRead(profileId, comic.id, issue?.id, if (read) 1 else 0).execute().body()

        if (updatedComic != null) {
            comicDao.update(updatedComic)
            issueDao.update(updatedComic.issues)
        }
    }

    companion object {
        private var profileId: Int = 0
    }
}