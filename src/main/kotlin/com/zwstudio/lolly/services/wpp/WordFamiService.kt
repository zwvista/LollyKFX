package com.zwstudio.lolly.services.wpp

import com.zwstudio.lolly.models.wpp.MWordFami
import com.zwstudio.lolly.restapi.wpp.RestWordFami
import com.zwstudio.lolly.services.misc.BaseService
import com.zwstudio.lolly.viewmodels.misc.Global
import io.reactivex.rxjava3.core.Observable

class WordFamiService: BaseService() {
    private fun getDataByWord(wordid: Int): Observable<List<MWordFami>> =
        retrofitJson.create(RestWordFami::class.java)
            .getDataByUserWord("USERID,eq,${Global.userid}", "WORDID,eq,$wordid")
            .map { it.lst!! }

    private fun update(o: MWordFami): Observable<Unit> =
        retrofitJson.create(RestWordFami::class.java)
            .update(o.id, o.userid, o.wordid, o.correct, o.total)
            .map { println(it.toString()) }

    private fun create(o: MWordFami): Observable<Int> =
        retrofitJson.create(RestWordFami::class.java)
            .create(o.userid, o.wordid, o.correct, o.total)
            .doAfterNext { println(it.toString()) }

    fun delete(id: Int): Observable<Unit> =
        retrofitJson.create(RestWordFami::class.java)
            .delete(id)
            .map { println(it.toString()) }

    fun update(wordid: Int, isCorrect: Boolean): Observable<MWordFami> =
        getDataByWord(wordid).flatMap { lst ->
            val d = if (isCorrect) 1 else 0
            val item = MWordFami().apply {
                userid = Global.userid
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
                val o = lst[0]
                item.id = o.id
                item.correct = o.correct + d
                item.total = o.total + 1
                update(item).map {
                    item
                }
            }
        }
}
