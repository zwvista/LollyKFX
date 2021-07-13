package com.zwstudio.lolly.restapi.wpp

import com.zwstudio.lolly.models.wpp.MLangPhrases
import com.zwstudio.lolly.models.wpp.MLangWords
import com.zwstudio.lolly.models.wpp.MWordsPhrases
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestWordPhrase {
    @GET("WORDSPHRASES")
    fun getDataByWordPhrase(@Query("filter") vararg filters: String): Single<MWordsPhrases>

    @GET("VPHRASESWORD")
    fun getPhrasesByWordId(@Query("filter") filter: String): Single<MLangPhrases>

    @GET("VWORDSPHRASE")
    fun getWordsByPhraseId(@Query("filter") filter: String): Single<MLangWords>

    @FormUrlEncoded
    @POST("WORDSPHRASES")
    fun create(@Field("WORDID") wordid: Int, @Field("PHRASEID") phraseid: Int): Single<Int>

    @FormUrlEncoded
    @DELETE("WORDSPHRASES")
    fun delete(@Field("ID") id: Int): Single<Int>

}
