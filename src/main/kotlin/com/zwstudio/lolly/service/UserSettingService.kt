package com.zwstudio.lolly.service

import com.zwstudio.lolly.domain.MUserSetting
import com.zwstudio.lolly.domain.MUserSettingInfo
import com.zwstudio.lolly.restapi.RestUserSetting
import io.reactivex.rxjava3.core.Observable

class UserSettingService: BaseService() {
    fun getDataByUser(userid: Int): Observable<List<MUserSetting>> =
        retrofitJson.create(RestUserSetting::class.java)
            .getDataByUser("USERID,eq,${userid}")
            .map { it.lst!! }

    fun update(info: MUserSettingInfo, v: Int): Observable<Unit> =
        update(info, v.toString())

    fun update(info: MUserSettingInfo, v: String): Observable<Unit> =
        when (info.valueid) {
            1 -> retrofitJson.create(RestUserSetting::class.java)
                .updateValue1(info.usersettingid, v)
                .map { println(it.toString()) }
            2 -> retrofitJson.create(RestUserSetting::class.java)
                .updateValue2(info.usersettingid, v)
                .map { println(it.toString()) }
            3 -> retrofitJson.create(RestUserSetting::class.java)
                .updateValue3(info.usersettingid, v)
                .map { println(it.toString()) }
            4 -> retrofitJson.create(RestUserSetting::class.java)
                .updateValue4(info.usersettingid, v)
                .map { println(it.toString()) }
            else -> Observable.empty()
        }
}
