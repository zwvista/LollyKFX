package com.zwstudio.lolly.services.misc

import com.zwstudio.lolly.common.*
import com.zwstudio.lolly.models.misc.MSelectItem
import com.zwstudio.lolly.models.misc.MTextbook
import com.zwstudio.lolly.restapi.misc.RestOnlineTextbook
import com.zwstudio.lolly.restapi.misc.RestTextbook
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import tornadofx.Component
import tornadofx.ScopedInstance

class TextbookService: Component(), ScopedInstance {
    private val api = retrofitJson.create(RestTextbook::class.java)
    private val apiSP = retrofitSP.create(RestTextbook::class.java)

    fun getDataByLang(langid: Int): Single<List<MTextbook>> {
        fun f(units: String): List<String> {
            var m = Regex("UNITS,(\\d+)").find(units)
            if (m != null) {
                val units = m.groupValues[1].toInt()
                return (1..units).map { it.toString() }
            }
            m = Regex("PAGES,(\\d+),(\\d+)").find(units)
            if (m != null) {
                val n1 = m.groupValues[1].toInt()
                val n2 = m.groupValues[2].toInt()
                val units = (n1 + n2 - 1) / n2
                return (1..units).map { "${it * n2 - n2 + 1}~${it * n2}" }
            }
            m = Regex("CUSTOM,(.+)").find(units)
            if (m != null)
                return m.groupValues[1].split(",")
            return listOf()
        }
        return api.getDataByLang("LANGID,eq,$langid")
            .map {
                val lst = it.lst
                for (o in lst) {
                    o.lstUnits = f(o.units).mapIndexed { index, s -> MSelectItem(index + 1, s) }
                    o.lstParts = o.parts.split(",").mapIndexed { index, s -> MSelectItem(index + 1, s) }
                }
                lst
            }
    }

    fun update(o: MTextbook): Completable =
        apiSP.update(o.id, o.langid, o.textbookname, o.units, o.parts)
            .completeUpdate(o.id)

    fun create(o: MTextbook): Single<Int> =
        apiSP.create(o.langid, o.textbookname, o.units, o.parts)
            .debugCreate()

    fun delete(id: Int): Completable =
        apiSP.delete(id).completeDelete()

}
