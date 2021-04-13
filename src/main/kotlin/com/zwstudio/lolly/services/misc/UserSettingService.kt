package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.viewmodels.misc.Global
import com.zwstudio.lolly.models.misc.MUserSetting
import com.zwstudio.lolly.models.misc.MUserSettingInfo
import com.zwstudio.lolly.restapi.misc.RestUserSetting
import io.reactivex.rxjava3.core.Observable

class UserSettingService: BaseService() {
    fun getData(): Observable<List<MUserSetting>> =
        retrofitJson.create(RestUserSetting::class.java)
            .getDataByUser("USERID,eq,${Global.userid}")
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
            else -> Observable.just(Unit)
        }
}
