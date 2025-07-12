package com.zwstudio.lolly.restapi.blogs

import com.zwstudio.lolly.models.blogs.MUnitBlogPost
import com.zwstudio.lolly.models.blogs.MUnitBlogPosts
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestUnitBlogPost {
    @GET("UNITBLOGPOSTS")
    fun getDataByTextbook(@Query("filter") vararg filters: String): Single<MUnitBlogPosts>

    @PUT("UNITBLOGPOSTS/{id}")
    fun update(@Path("id") id: Int, @Body item: MUnitBlogPost): Single<Int>

    @POST("UNITBLOGPOSTS")
    fun create(@Body item: MUnitBlogPost): Single<Int>
}
