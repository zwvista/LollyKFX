package com.zwstudio.lolly.restapi.misc

import com.zwstudio.lolly.models.misc.MCodes
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RestCode {
    @GET("CODES?filter=KIND,eq,1")
    fun getDictCodes(): Single<MCodes>
    @GET("CODES?filter=KIND,eq,3")
    fun getReadNumberCodes(): Single<MCodes>
}
