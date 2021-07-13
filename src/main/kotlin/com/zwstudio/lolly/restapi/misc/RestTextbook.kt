package com.zwstudio.lolly.restapi.misc

import com.zwstudio.lolly.models.misc.MTextbooks
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestTextbook {
    @GET("TEXTBOOKS")
    fun getDataByLang(@Query("filter") filter: String): Single<MTextbooks>

    @FormUrlEncoded
    @PUT("TEXTBOOKS/{id}")
    fun update(@Path("id") id: Int, @Field("LANGID") langid: Int,
               @Field("NAME") textbookname: String, @Field("UNITS") units: String,
               @Field("PARTS") parts: String): Single<Int>

    @FormUrlEncoded
    @POST("TEXTBOOKS")
    fun create(@Field("LANGID") langid: Int,
               @Field("NAME") textbookname: String, @Field("UNITS") units: String,
               @Field("PARTS") parts: String): Single<Int>

    @DELETE("TEXTBOOKS/{id}")
    fun delete(@Path("id") id: Int): Single<Int>
}
