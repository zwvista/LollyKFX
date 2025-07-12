package com.zwstudio.lolly.services.blogs

import com.zwstudio.lolly.common.completeCreate
import com.zwstudio.lolly.common.completeUpdate
import com.zwstudio.lolly.common.retrofitJson
import com.zwstudio.lolly.models.blogs.MUnitBlogPost
import com.zwstudio.lolly.restapi.blogs.RestUnitBlogPost
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance
import java.util.*

class UnitBlogPostService: Component(), ScopedInstance {
    private val api = retrofitJson.create(RestUnitBlogPost::class.java)

    fun getDataByTextbook(textbookid: Int, unit: Int): Single<Optional<MUnitBlogPost>> =
        api.getDataByTextbook("TEXTBOOKID,eq,${textbookid}", "UNIT,eq,${unit}").map {
            Optional.ofNullable(it.lst.firstOrNull())
        }

    private fun create(item: MUnitBlogPost): Completable =
        api.create(item).completeCreate()

    private fun update(item: MUnitBlogPost): Completable =
        api.update(item.id, item).completeUpdate(item.id)

    fun update(textbookid: Int, unit: Int, content: String): Completable =
        getDataByTextbook(textbookid, unit).map {
            it.orElse(MUnitBlogPost().apply {
                this.textbookid = textbookid
                this.unit = unit
            })
        }.flatMapCompletable {
            it.content = content
            if (it.id == 0) create(it) else update(it)
        }
}
