package com.zwstudio.lolly.data

import com.zwstudio.lolly.domain.MUnitPhrase
import com.zwstudio.lolly.service.LangPhraseService
import com.zwstudio.lolly.service.UnitPhraseService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PhrasesUnitViewModel : BaseViewModel2() {

    var lstPhrases = listOf<MUnitPhrase>()
    var isSwipeStarted = false

    val compositeDisposable = CompositeDisposable()

    val unitPhraseService: UnitPhraseService by inject()
    val langPhraseService: LangPhraseService by inject()

    fun getDataInTextbook(): Observable<Unit> =
        unitPhraseService.getDataByTextbookUnitPart(vmSettings.selectedTextbook,
                vmSettings.usunitpartfrom, vmSettings.usunitpartto)
            .map { lstPhrases = it }
            .applyIO()

    fun getDataInLang(): Observable<Unit> =
        unitPhraseService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks)
            .map { lstPhrases = it }
            .applyIO()

    fun updateSeqNum(id: Int, seqnum: Int): Observable<Unit> =
        unitPhraseService.updateSeqNum(id, seqnum)
            .applyIO()

    fun update(item: MUnitPhrase): Observable<Unit> =
        unitPhraseService.update(item)
            .applyIO()

    fun create(item: MUnitPhrase): Observable<Int> =
        unitPhraseService.create(item)
            .applyIO()

    fun delete(item: MUnitPhrase): Observable<Unit> =
        unitPhraseService.delete(item)
            .applyIO()

    fun reindex(onNext: (Int) -> Unit) {
        for (i in 1..lstPhrases.size) {
            val item = lstPhrases[i - 1]
            if (item.seqnum == i) continue
            item.seqnum = i
            compositeDisposable.add(updateSeqNum(item.id, i).subscribe {
                onNext(i - 1)
            })
        }
    }

    fun newUnitPhrase() = MUnitPhrase().apply {
        langid = vmSettings.selectedLang.id
        textbookid = vmSettings.ustextbookid
        // https://stackoverflow.com/questions/33640864/how-to-sort-based-on-compare-multiple-values-in-kotlin
        val maxItem = lstPhrases.maxWith(compareBy({ it.unit }, { it.part }, { it.seqnum }))
        unit = maxItem?.unit ?: vmSettings.usunitto
        part = maxItem?.part ?: vmSettings.uspartto
        seqnum = (maxItem?.seqnum ?: 0) + 1
    }

}
