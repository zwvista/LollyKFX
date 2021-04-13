package com.zwstudio.lolly.restapi.wpp

import com.zwstudio.lolly.models.wpp.MLangPhrases
import com.zwstudio.lolly.models.wpp.MLangWords
import com.zwstudio.lolly.models.wpp.MWordsPhrases
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface RestWordPhrase {
    @GET("WORDSPHRASES")
    fun getDataByWordPhrase(@Query("filter") vararg filters: String): Observable<MWordsPhrases>

    @GET("VPHRASESWORD")
    fun getPhrasesByWordId(@Query("filter") filter: String): Observable<MLangPhrases>

    @GET("VWORDSPHRASE")
    fun getWordsByPhraseId(@Query("filter") filter: String): Observable<MLangWords>

    @FormUrlEncoded
    @POST("WORDSPHRASES")
    fun create(@Field("WORDID") wordid: Int, @Field("PHRASEID") phraseid: Int): Observable<Int>

    @FormUrlEncoded
    @DELETE("WORDSPHRASES")
    fun delete(@Field("ID") id: Int): Observable<Int>

}
