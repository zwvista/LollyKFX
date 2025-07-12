package com.zwstudio.lolly.restapi.blogs

import com.zwstudio.lolly.models.blogs.MLangBlogGPs
import com.zwstudio.lolly.models.blogs.MLangBlogGroup
import com.zwstudio.lolly.models.blogs.MLangBlogGroups
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestLangBlogGroup {
    @GET("LANGBLOGGROUPS")
    fun getDataByLang(@Query("filter") filter: String, @Query("order") order: String): Single<MLangBlogGroups>

    @GET("VLANGBLOGGP?order=GROUPNAME")
    fun getDataByLangPost(@Query("filter") vararg filters: String): Single<MLangBlogGPs>

    @POST("LANGBLOGGROUPS")
    fun create(@Body item: MLangBlogGroup): Single<Int>

    @PUT("LANGBLOGGROUPS/{id}")
    fun update(@Path("id") id: Int, @Body item: MLangBlogGroup): Single<Int>

    @DELETE("LANGBLOGGROUPS/{id}")
    fun delete(@Path("id") id: Int): Single<Int>
}
