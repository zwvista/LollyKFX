package com.zwstudio.lolly.restapi

import com.zwstudio.lolly.domain.MUserSettings
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

// https://stackoverflow.com/questions/46892100/how-to-use-rxjava2-with-retrofit-in-android
interface RestUserSetting {
    @GET("USERSETTINGS")
    fun getDataByUser(@Query("filter") filter: String): Observable<MUserSettings>

    @FormUrlEncoded
    @PUT("USERSETTINGS/{id}")
    fun updateValue1(@Path("id") id: Int, @Field("VALUE1") v: String): Observable<Int>

    @FormUrlEncoded
    @PUT("USERSETTINGS/{id}")
    fun updateValue2(@Path("id") id: Int, @Field("VALUE2") v: String): Observable<Int>

    @FormUrlEncoded
    @PUT("USERSETTINGS/{id}")
    fun updateValue3(@Path("id") id: Int, @Field("VALUE3") v: String): Observable<Int>

    @FormUrlEncoded
    @PUT("USERSETTINGS/{id}")
    fun updateValue4(@Path("id") id: Int, @Field("VALUE4") v: String): Observable<Int>
}
