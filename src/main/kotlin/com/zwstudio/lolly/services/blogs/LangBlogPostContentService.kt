package com.zwstudio.lolly.services.blogs

import com.zwstudio.lolly.common.completeUpdate
import com.zwstudio.lolly.common.retrofitJson
import com.zwstudio.lolly.models.blogs.MLangBlogPostContent
import com.zwstudio.lolly.restapi.blogs.RestLangBlogPostContent
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance
import java.util.*

class LangBlogPostContentService: Component(), ScopedInstance {
    private val api = retrofitJson.create(RestLangBlogPostContent::class.java)

    fun getDataById(id: Int): Single<Optional<MLangBlogPostContent>> =
        api.getDataById("ID,eq,$id").map {
            Optional.ofNullable(it.lst.firstOrNull())
        }

    fun update(item: MLangBlogPostContent): Completable =
        api.update(item.id, item).completeUpdate(item.id)
}
