package com.zwstudio.lolly.restapi.misc

import com.zwstudio.lolly.domain.misc.MDictionaries
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface RestDictionary {
    @GET("VDICTIONARIES?order=SEQNUM&order=DICTNAME")
    fun getDictsByLang(@Query("filter") filter: String): Observable<MDictionaries>
    @GET("VDICTSREFERENCE?order=SEQNUM&order=DICTNAME")
    fun getDictsReferenceByLang(@Query("filter") filter: String): Observable<MDictionaries>
    @GET("VDICTSNOTE")
    fun getDictsNoteByLang(@Query("filter") filter: String): Observable<MDictionaries>
    @GET("VDICTSTRANSLATION")
    fun getDictsTranslationByLang(@Query("filter") filter: String): Observable<MDictionaries>

    @FormUrlEncoded
    @PUT("DICTIONARIES/{id}")
    fun update(@Path("id") id: Int, @Field("DICTID") dictid: Int,
               @Field("LANGIDFROM") langidfrom: Int, @Field("LANGIDTO") langidto: Int,
               @Field("SEQNUM") seqnum: Int, @Field("DICTTYPEID") dicttypeid: Int,
               @Field("DICTNAME") dictname: String, @Field("URL") url: String?,
               @Field("CHCONV") chconv: String?, @Field("AUTOMATION") automation: String?,
               @Field("TRANSFORM") transform: String?, @Field("WAIT") wait: Int,
               @Field("TEMPLATE") template: String?, @Field("TEMPLATE2") template2: String?): Observable<Int>

    @FormUrlEncoded
    @POST("DICTIONARIES")
    fun create(@Field("DICTID") dictid: Int,
               @Field("LANGIDFROM") langidfrom: Int, @Field("LANGIDTO") langidto: Int,
               @Field("SEQNUM") seqnum: Int, @Field("DICTTYPEID") dicttypeid: Int,
               @Field("DICTNAME") dictname: String, @Field("URL") url: String?,
               @Field("CHCONV") chconv: String?, @Field("AUTOMATION") automation: String?,
               @Field("TRANSFORM") transform: String?, @Field("WAIT") wait: Int,
               @Field("TEMPLATE") template: String?, @Field("TEMPLATE2") template2: String?): Observable<Int>

    @DELETE("DICTIONARIES/{id}")
    fun delete(@Path("id") id: Int): Observable<Int>
}
