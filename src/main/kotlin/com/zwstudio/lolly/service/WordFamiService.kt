package com.zwstudio.lolly.service

import com.zwstudio.lolly.domain.wpp.MWordFami
import com.zwstudio.lolly.restapi.RestWordFami
import io.reactivex.rxjava3.core.Observable

class WordFamiService: BaseService() {
    fun getDataByUserWord(userid: Int, wordid: Int): Observable<List<MWordFami>> =
        retrofitJson.create(RestWordFami::class.java)
            .getDataByUserWord("USERID,eq,$userid", "WORDID,eq,$wordid")
            .map { it.lst!! }

    fun update(id: Int, userid: Int, wordid: Int, level: Int, correct: Int, total: Int): Observable<Unit> =
        retrofitJson.create(RestWordFami::class.java)
            .update(id, userid, wordid, level, correct, total)
            .map { println(it.toString()) }

    fun create(userid: Int, wordid: Int, level: Int, correct: Int, total: Int): Observable<Unit> =
        retrofitJson.create(RestWordFami::class.java)
            .create(userid, wordid, level, correct, total)
            .map { println(it.toString()) }

    fun delete(id: Int): Observable<Unit> =
        retrofitJson.create(RestWordFami::class.java)
            .delete(id)
            .map { println(it.toString()) }
}
