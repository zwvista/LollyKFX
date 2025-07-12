package com.zwstudio.lolly.restapi.wpp

import com.zwstudio.lolly.models.misc.MSPResult
import com.zwstudio.lolly.models.wpp.MPattern
import com.zwstudio.lolly.models.wpp.MPatterns
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestPattern {
    @GET("PATTERNS?order=PATTERN")
    fun getDataByLang(@Query("filter") filter: String): Single<MPatterns>

    @GET("PATTERNS")
    fun getDataById(@Query("filter") filter: String): Single<MPatterns>

    @FormUrlEncoded
    @PUT("PATTERNS/{id}")
    fun updateNote(@Path("id") id: Int, @Field("NOTE") note: String): Single<Int>

    @PUT("PATTERNS/{id}")
    fun update(@Path("id") id: Int, @Body item: MPattern): Single<Int>

    @POST("PATTERNS")
    fun create(@Body item: MPattern): Single<Int>

    @DELETE("PATTERNS/{id}")
    fun delete(@Path("id") id: Int): Single<Int>
}
