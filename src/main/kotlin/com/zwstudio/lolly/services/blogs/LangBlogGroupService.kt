package com.zwstudio.lolly.services.blogs

import com.zwstudio.lolly.common.completeDelete
import com.zwstudio.lolly.common.completeUpdate
import com.zwstudio.lolly.common.debugCreate
import com.zwstudio.lolly.common.retrofitJson
import com.zwstudio.lolly.models.blogs.MLangBlogGroup
import com.zwstudio.lolly.restapi.blogs.RestLangBlogGroup
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class LangBlogGroupService: Component(), ScopedInstance {
    private val api = retrofitJson.create(RestLangBlogGroup::class.java)

    fun getDataByLang(langid: Int): Single<List<MLangBlogGroup>> =
        api.getDataByLang("LANGID,eq,$langid", "NAME").map {
            it.lst
        }

    fun getDataByLangPost(langid: Int, postid: Int): Single<List<MLangBlogGroup>> =
        api.getDataByLangPost("LANGID,eq,$langid", "POSTID,eq,$postid").map {
            it.lst.map { item ->
                MLangBlogGroup(
                    id = item.groupid,
                    langid = langid,
                    groupname = item.groupname,
                ).also { it.gpid = item.id }
            }.distinctBy { it.id }
        }

    fun create(item: MLangBlogGroup): Single<Int> =
        api.create(item).debugCreate()

    fun update(item: MLangBlogGroup): Completable =
        api.update(item.id, item).completeUpdate(item.id)

    fun delete(id: Int): Completable =
        api.delete(id).completeDelete()
}
