package com.zwstudio.lolly.restapi.misc

import com.zwstudio.lolly.models.misc.MWebTextbooks
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RestWebTextbook {
    @GET("VWEBTEXTBOOKS")
    fun getDataByLang(@Query("filter") filter: String): Single<MWebTextbooks>
}
