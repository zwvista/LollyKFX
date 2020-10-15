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
    fun updateDict(@Path("id") id: Int,
                   @Field("LANGIDFROM") langidfrom: Int, @Field("LANGIDTO") langidto: Int,
                   @Field("NAME") dictname: String, @Field("SEQNUM") seqnum: Int,
                   @Field("DICTTYPECODE") dicttypecode: Int, @Field("URL") url: String?,
                   @Field("CHCONV") chconv: String?, @Field("AUTOMATION") automation: String?,
                   @Field("TEMPLATE") template: String?, @Field("TEMPLATE2") template2: String?): Observable<Int>

    @FormUrlEncoded
    @POST("DICTIONARIES")
    fun createDict(@Field("LANGIDFROM") langidfrom: Int, @Field("LANGIDTO") langidto: Int,
                   @Field("NAME") dictname: String, @Field("SEQNUM") seqnum: Int,
                   @Field("DICTTYPECODE") dicttypecode: Int, @Field("URL") url: String?,
                   @Field("CHCONV") chconv: String?, @Field("AUTOMATION") automation: String?,
                   @Field("TEMPLATE") template: String?, @Field("TEMPLATE2") template2: String?): Observable<Int>

    @DELETE("DICTIONARIES/{id}")
    fun deleteDict(@Path("id") id: Int): Observable<Int>

    @FormUrlEncoded
    @PUT("SITES/{id}")
    fun updateSite(@Path("id") siteid: Int, @Field("NAME") sitename: String,
               @Field("TRANSFORM") transform: String?, @Field("WAIT") wait: Int): Observable<Int>

    @FormUrlEncoded
    @POST("SITES")
    fun createSite(@Field("NAME") sitename: String,
               @Field("TRANSFORM") transform: String?, @Field("WAIT") wait: Int,): Observable<Int>

    @DELETE("SITES/{id}")
    fun deleteSite(@Path("id") siteid: Int): Observable<Int>
}
