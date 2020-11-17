package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import com.zwstudio.lolly.service.wpp.UnitPhraseService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import tornadofx.*

class PhrasesUnitViewModel(val inTextbook: Boolean) : PhrasesBaseViewModel() {

    var lstPhrasesAll = mutableListOf<MUnitPhrase>()
    val lstPhrases = mutableListOf<MUnitPhrase>().asObservable()
    val compositeDisposable = CompositeDisposable()
    val unitPhraseService: UnitPhraseService by inject()
    val noFilter get() = textFilter.value.isEmpty() && textbookFilter.value.value == 0

    override fun applyFilters() {
        lstPhrases.setAll(if (noFilter) lstPhrasesAll else lstPhrasesAll.filter {
            (textFilter.value.isEmpty() || (if (scopeFilter.value == "Phrase") it.phrase else it.translation).contains(textFilter.value, true)) &&
            (textbookFilter.value.value == 0 || it.textbookid == textbookFilter.value.value)
        })
        statusText.value = "${lstPhrases.size} Phrases in ${if (inTextbook) vmSettings.unitInfo else vmSettings.langInfo}"
    }

    fun reload() {
        (if (inTextbook)
            unitPhraseService.getDataByTextbookUnitPart(vmSettings.selectedTextbook, vmSettings.usunitpartfrom, vmSettings.usunitpartto)
        else
            unitPhraseService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks))
            .applyIO()
            .subscribe { lstPhrasesAll = it.toMutableList(); applyFilters() }
        textbookFilter.value = vmSettings.lstTextbookFilters[0]
    }

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
        for (i in 1..lstPhrasesAll.size) {
            val item = lstPhrasesAll[i - 1]
            if (item.seqnum == i) continue
            item.seqnum = i
            compositeDisposable.add(updateSeqNum(item.id, i).subscribe {
                onNext(i - 1)
            })
        }
    }

    fun newUnitPhrase() = MUnitPhrase().apply {
        langid = vmSettings.selectedLang.id
        textbookid = vmSettings.ustextbook
        // https://stackoverflow.com/questions/33640864/how-to-sort-based-on-compare-multiple-values-in-kotlin
        val maxItem = lstPhrasesAll.maxWithOrNull(compareBy({ it.unit }, { it.part }, { it.seqnum }))
        unit = maxItem?.unit ?: vmSettings.usunitto
        part = maxItem?.part ?: vmSettings.uspartto
        seqnum = (maxItem?.seqnum ?: 0) + 1
        textbook = vmSettings.selectedTextbook
    }
}
