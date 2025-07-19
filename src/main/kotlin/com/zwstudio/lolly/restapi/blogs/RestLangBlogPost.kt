package com.zwstudio.lolly.restapi.blogs

import com.zwstudio.lolly.models.blogs.MLangBlogGPs
import com.zwstudio.lolly.models.blogs.MLangBlogPost
import com.zwstudio.lolly.models.blogs.MLangBlogPosts
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestLangBlogPost {
    @GET("LANGBLOGPOSTS")
    fun getDataByLang(@Query("filter") filter: String): Single<MLangBlogPosts>

    @GET("VLANGBLOGGP?order=TITLE")
    fun getDataByLangGroup(@Query("filter") vararg filters: String): Single<MLangBlogGPs>

    @PUT("LANGBLOGPOSTS/{id}")
    fun update(@Path("id") id: Int, @Body item: MLangBlogPost): Single<Int>

    @POST("LANGBLOGPOSTS")
    fun create(@Body item: MLangBlogPost): Single<Int>

    @DELETE("LANGBLOGPOSTS/{id}")
    fun delete(@Path("id") id: Int): Single<Int>
}
