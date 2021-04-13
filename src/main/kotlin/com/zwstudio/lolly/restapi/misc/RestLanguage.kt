package com.zwstudio.lolly.restapi.misc

import com.zwstudio.lolly.models.misc.MLanguages
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface RestLanguage {
    @GET("LANGUAGES")
    fun getData(): Observable<MLanguages>
}
