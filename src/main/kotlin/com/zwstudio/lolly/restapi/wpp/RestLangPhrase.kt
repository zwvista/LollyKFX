package com.zwstudio.lolly.restapi.wpp

import com.zwstudio.lolly.models.misc.MSPResult
import com.zwstudio.lolly.models.wpp.MLangPhrase
import com.zwstudio.lolly.models.wpp.MLangPhrases
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestLangPhrase {
    @GET("LANGPHRASES?order=PHRASE")
    fun getDataByLang(@Query("filter") filter: String): Single<MLangPhrases>

    @GET("LANGPHRASES")
    fun getDataByLangPhrase(@Query("filter") vararg filters: String): Single<MLangPhrases>

    @GET("LANGPHRASES")
    fun getDataById(@Query("filter") filter: String): Single<MLangPhrases>

    @FormUrlEncoded
    @PUT("LANGPHRASES/{id}")
    fun updateTranslation(@Path("id") id: Int, @Field("TRANSLATION") translation: String?): Single<Int>

    @PUT("LANGPHRASES/{id}")
    fun update(@Path("id") id: Int, @Body item: MLangPhrase): Single<Int>

    @POST("LANGPHRASES")
    fun create(@Body item: MLangPhrase): Single<Int>

    @FormUrlEncoded
    @POST("LANGPHRASES_DELETE")
    fun delete(@Field("P_ID") id: Int, @Field("P_LANGID") langid: Int,
               @Field("P_PHRASE") phrase: String,
               @Field("P_TRANSLATION") translation: String?): Single<List<List<MSPResult>>>
}
