package com.zwstudio.lolly.service.wpp

import com.zwstudio.lolly.domain.wpp.MWordFami
import com.zwstudio.lolly.restapi.wpp.RestWordFami
import com.zwstudio.lolly.service.misc.BaseService
import io.reactivex.rxjava3.core.Observable

class WordFamiService: BaseService() {
    fun getDataByUserWord(userid: Int, wordid: Int): Observable<List<MWordFami>> =
        retrofitJson.create(RestWordFami::class.java)
            .getDataByUserWord("USERID,eq,$userid", "WORDID,eq,$wordid")
            .map { it.lst!! }

    fun update(id: Int, userid: Int, wordid: Int, correct: Int, total: Int): Observable<Unit> =
        retrofitJson.create(RestWordFami::class.java)
            .update(id, userid, wordid, correct, total)
            .map { println(it.toString()) }

    fun create(userid: Int, wordid: Int, correct: Int, total: Int): Observable<Int> =
        retrofitJson.create(RestWordFami::class.java)
            .create(userid, wordid, correct, total)
            .doAfterNext { println(it.toString()) }

    fun delete(id: Int): Observable<Unit> =
        retrofitJson.create(RestWordFami::class.java)
            .delete(id)
            .map { println(it.toString()) }
}
