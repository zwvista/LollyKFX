package com.zwstudio.lolly.restapi.misc

import com.zwstudio.lolly.models.misc.MDictionaries
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
    fun update(@Path("id") id: Int,
                   @Field("LANGIDFROM") langidfrom: Int, @Field("LANGIDTO") langidto: Int,
                   @Field("NAME") dictname: String, @Field("SEQNUM") seqnum: Int,
                   @Field("DICTTYPECODE") dicttypecode: Int, @Field("URL") url: String,
                   @Field("CHCONV") chconv: String, @Field("AUTOMATION") automation: String,
                   @Field("TEMPLATE") template: String, @Field("TEMPLATE2") template2: String): Observable<Int>

    @FormUrlEncoded
    @POST("DICTIONARIES")
    fun create(@Field("LANGIDFROM") langidfrom: Int, @Field("LANGIDTO") langidto: Int,
                   @Field("NAME") dictname: String, @Field("SEQNUM") seqnum: Int,
                   @Field("DICTTYPECODE") dicttypecode: Int, @Field("URL") url: String,
                   @Field("CHCONV") chconv: String, @Field("AUTOMATION") automation: String,
                   @Field("TEMPLATE") template: String, @Field("TEMPLATE2") template2: String): Observable<Int>

    @DELETE("DICTIONARIES/{id}")
    fun delete(@Path("id") id: Int): Observable<Int>
}
