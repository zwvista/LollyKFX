package com.zwstudio.lolly.restapi.wpp

import com.zwstudio.lolly.models.wpp.MPatternWebPage
import com.zwstudio.lolly.models.wpp.MPatternWebPages
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestPatternWebPage {
    @GET("VPATTERNSWEBPAGES?order=SEQNUM")
    fun getDataByPattern(@Query("filter") filter: String): Single<MPatternWebPages>

    @GET("VPATTERNSWEBPAGES")
    fun getDataById(@Query("filter") filter: String): Single<MPatternWebPages>

    @FormUrlEncoded
    @PUT("PATTERNSWEBPAGES/{id}")
    fun updateSeqNum(@Path("id") id: Int, @Field("SEQNUM") seqnum: Int): Single<Int>

    @PUT("PATTERNSWEBPAGES/{id}")
    fun update(@Path("id") id: Int, @Body item: MPatternWebPage): Single<Int>

    @POST("PATTERNSWEBPAGES")
    fun create(@Body item: MPatternWebPage): Single<Int>

    @DELETE("PATTERNSWEBPAGES/{id}")
    fun delete(@Path("id") id: Int): Single<Int>
}
