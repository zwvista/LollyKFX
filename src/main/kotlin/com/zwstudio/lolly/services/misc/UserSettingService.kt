package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.common.GlobalUser
import com.zwstudio.lolly.common.retrofitJson
import com.zwstudio.lolly.models.misc.MUserSetting
import com.zwstudio.lolly.models.misc.MUserSettingInfo
import com.zwstudio.lolly.restapi.misc.RestUserSetting
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class UserSettingService: Component(), ScopedInstance {
    private val api = retrofitJson.create(RestUserSetting::class.java)

    fun getData(): Single<List<MUserSetting>> =
        api.getDataByUser("USERID,eq,${GlobalUser.userid}")
            .map { it.lst }

    fun update(info: MUserSettingInfo, v: Int): Completable =
        update(info, v.toString())

    fun update(info: MUserSettingInfo, v: String): Completable =
        (when (info.valueid) {
            1 -> api.updateValue1(info.usersettingid, v)
                .map { println(it.toString()) }
            2 -> api.updateValue2(info.usersettingid, v)
                .map { println(it.toString()) }
            3 -> api.updateValue3(info.usersettingid, v)
                .map { println(it.toString()) }
            4 -> api.updateValue4(info.usersettingid, v)
                .map { println(it.toString()) }
            else -> Single.just(0)
        }).flatMapCompletable { Completable.complete() }
}
