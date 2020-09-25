package com.zwstudio.lolly.restapi.wpp

import com.zwstudio.lolly.domain.wpp.MPatternPhrases
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface RestPatternPhrase {
    @GET("VPATTERNSPHRASES")
    fun getDataByPatternId(@Query("filter") filter: String): Observable<MPatternPhrases>

    @GET("VPATTERNSPHRASES")
    fun getDataByPatternIdPhraseId(@Query("filter") vararg filters: String): Observable<MPatternPhrases>

    @GET("VPATTERNSPHRASES")
    fun getDataByPhraseId(@Query("filter") filter: String): Observable<MPatternPhrases>

    @GET("VPATTERNSPHRASES")
    fun getDataById(@Query("filter") filter: String): Observable<MPatternPhrases>

    @FormUrlEncoded
    @PUT("PATTERNSPHRASES/{id}")
    fun update(@Path("id") id: Int, @Field("PATTERNID") patternid: Int,
               @Field("SEQNUM") seqnum: Int, @Field("PHRASEID") phraseid: Int): Observable<Int>

    @FormUrlEncoded
    @POST("PATTERNSPHRASES")
    fun create(@Field("PATTERNID") patternid: Int,
               @Field("SEQNUM") seqnum: Int, @Field("PHRASEID") phraseid: Int): Observable<Int>

    @DELETE("PATTERNSPHRASES/{id}")
    fun delete(@Path("id") id: Int): Observable<Int>
}
