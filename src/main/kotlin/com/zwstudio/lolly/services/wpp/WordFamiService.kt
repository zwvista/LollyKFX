package com.zwstudio.lolly.services.wpp

import com.zwstudio.lolly.common.GlobalUser
import com.zwstudio.lolly.common.completeDelete
import com.zwstudio.lolly.common.completeUpdate
import com.zwstudio.lolly.common.debugCreate
import com.zwstudio.lolly.models.wpp.MWordFami
import com.zwstudio.lolly.restapi.wpp.RestWordFami
import com.zwstudio.lolly.services.misc.BaseService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class WordFamiService: BaseService() {
    private val api = retrofitJson.create(RestWordFami::class.java)

    private fun getDataByWord(wordid: Int): Single<List<MWordFami>> =
        api.getDataByUserWord("USERID,eq,${GlobalUser.userid}", "WORDID,eq,$wordid")
            .map { it.lst }

    private fun update(item: MWordFami): Completable =
        api.update(item.id, item).completeUpdate(item.id)

    private fun create(item: MWordFami): Single<Int> =
        api.create(item).debugCreate()

    fun delete(id: Int): Completable =
        api.delete(id).completeDelete()

    fun update(wordid: Int, isCorrect: Boolean): Single<MWordFami> =
        getDataByWord(wordid).flatMap { lst ->
            val d = if (isCorrect) 1 else 0
            val item = MWordFami().apply {
                userid = GlobalUser.userid
                this.wordid = wordid
            }
            if (lst.isEmpty()) {
                item.correct = d
                item.total = 1
                create(item).map {
                    item.id = it
                    item
                }
            }
            else {
                val item2 = lst[0]
                item.id = item2.id
                item.correct = item2.correct + d
                item.total = item2.total + 1
                update(item).toSingle {
                    item
                }
            }
        }
}
