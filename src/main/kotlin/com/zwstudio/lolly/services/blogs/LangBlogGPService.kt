package com.zwstudio.lolly.services.blogs

import com.zwstudio.lolly.common.completeDelete
import com.zwstudio.lolly.common.completeUpdate
import com.zwstudio.lolly.common.debugCreate
import com.zwstudio.lolly.models.blogs.MLangBlogGP
import com.zwstudio.lolly.restapi.blogs.RestLangBlogGP
import com.zwstudio.lolly.views.LollyApp.Companion.retrofitJson
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LangBlogGPService {
    private val api = retrofitJson.create(RestLangBlogGP::class.java)

    fun create(item: MLangBlogGP): Single<Int> =
        api.create(item).debugCreate()

    fun update(item: MLangBlogGP): Completable =
        api.update(item.id, item).completeUpdate(item.id)

    fun delete(id: Int): Completable =
        api.delete(id).completeDelete()
}
