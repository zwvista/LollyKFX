package com.zwstudio.lolly.restapi.misc

import com.zwstudio.lolly.domain.misc.MTextbooks
import com.zwstudio.lolly.domain.misc.MWebTextbook
import com.zwstudio.lolly.domain.misc.MWebTextbooks
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface RestWebTextbook {
    @GET("VWEBTEXTBOOKS")
    fun getDataByLang(@Query("filter") filter: String): Observable<MWebTextbooks>
}
