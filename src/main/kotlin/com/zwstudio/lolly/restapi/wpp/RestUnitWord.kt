package com.zwstudio.lolly.restapi.wpp

import com.zwstudio.lolly.models.misc.MSPResult
import com.zwstudio.lolly.models.wpp.MUnitWords
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestUnitWord {
    @GET("VUNITWORDS?order=UNITPART&order=SEQNUM")
    fun getDataByTextbookUnitPart(@Query("filter") vararg filters: String): Single<MUnitWords>

    @GET("VUNITWORDS?order=WORDID")
    fun getDataByTextbook(@Query("filter") filter: String): Single<MUnitWords>

    @GET("VUNITWORDS?order=TEXTBOOKID&order=UNIT&order=PART&order=SEQNUM")
    fun getDataByLang(@Query("filter") filter: String): Single<MUnitWords>

    @GET("VUNITWORDS")
    fun getDataByLangWord(@Query("filter") vararg filters: String): Single<MUnitWords>

    @FormUrlEncoded
    @PUT("UNITWORDS/{id}")
    fun updateSeqNum(@Path("id") id: Int, @Field("SEQNUM") seqnum: Int): Single<Int>

    @FormUrlEncoded
    @POST("UNITWORDS_UPDATE")
    fun update(@Field("P_ID") id: Int, @Field("P_LANGID") langid: Int,
               @Field("P_TEXTBOOKID") textbookid: Int,
               @Field("P_UNIT") unit: Int, @Field("P_PART") part: Int,
               @Field("P_SEQNUM") seqnum: Int, @Field("P_WORDID") wordid: Int,
               @Field("P_WORD") word: String, @Field("P_NOTE") note: String,
               @Field("P_FAMIID") famiid: Int,
               @Field("P_CORRECT") correct: Int, @Field("P_TOTAL") total: Int): Single<List<List<MSPResult>>>

    @FormUrlEncoded
    @POST("UNITWORDS_CREATE")
    fun create(@Field("P_ID") id: Int, @Field("P_LANGID") langid: Int,
               @Field("P_TEXTBOOKID") textbookid: Int,
               @Field("P_UNIT") unit: Int, @Field("P_PART") part: Int,
               @Field("P_SEQNUM") seqnum: Int, @Field("P_WORDID") wordid: Int,
               @Field("P_WORD") word: String, @Field("P_NOTE") note: String,
               @Field("P_FAMIID") famiid: Int,
               @Field("P_CORRECT") correct: Int, @Field("P_TOTAL") total: Int): Single<List<List<MSPResult>>>

    @FormUrlEncoded
    @POST("UNITWORDS_DELETE")
    fun delete(@Field("P_ID") id: Int, @Field("P_LANGID") langid: Int,
               @Field("P_TEXTBOOKID") textbookid: Int,
               @Field("P_UNIT") unit: Int, @Field("P_PART") part: Int,
               @Field("P_SEQNUM") seqnum: Int, @Field("P_WORDID") wordid: Int,
               @Field("P_WORD") word: String, @Field("P_NOTE") note: String,
               @Field("P_FAMIID") famiid: Int,
               @Field("P_CORRECT") correct: Int, @Field("P_TOTAL") total: Int): Single<List<List<MSPResult>>>

}
