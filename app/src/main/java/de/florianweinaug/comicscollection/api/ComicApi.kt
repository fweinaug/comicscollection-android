package de.florianweinaug.comicscollection.api

import de.florianweinaug.comicscollection.model.Comic
import de.florianweinaug.comicscollection.model.Issue
import de.florianweinaug.comicscollection.model.Publisher
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ComicApi {
    @GET("comic/comics")
    fun getComics(@Query("profileId") profileId: Int): Call<List<Comic>>

    @GET("publisher/publishers")
    fun getPublishers() : Call<List<Publisher>>

    @FormUrlEncoded
    @POST("comic/create")
    fun createComic(@Query("profileId") profileId: Int, @Field("name") name: String, @Field("publisher_id") publisherId: Int,
                    @Field("concluded") concluded: Boolean) : Call<Comic>

    @Multipart
    @POST("issue/create")
    fun createIssue(@Query("profileId") profileId: Int, @Part("comic_id") comicId: RequestBody, @Part image: MultipartBody.Part)
            : Call<Comic>

    @FormUrlEncoded
    @POST("issue/update")
    fun updateIssue(@Query("profileId") profileId: Int, @Query("issueId") issueId: Int, @Field("title") title: String, @Field("summary") summary: String)
            : Call<Issue>

    @FormUrlEncoded
    @POST("comic/read")
    fun setRead(@Field("profileId") profileId: Int, @Field("comicId") comicId: Int,
                @Field("issueId") issueId: Int?, @Field("read") read: Int): Call<Comic>
}