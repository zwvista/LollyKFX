package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.data.BaseViewModel
import com.zwstudio.lolly.data.applyIO
import com.zwstudio.lolly.domain.MSelectItem
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import com.zwstudio.lolly.service.UnitPhraseService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.asObservable

class PhrasesUnitViewModel(val inTextbook: Boolean) : BaseViewModel() {

    var lstPhrasesAll = mutableListOf<MUnitPhrase>()
    val lstPhrases = mutableListOf<MUnitPhrase>().asObservable()
    val compositeDisposable = CompositeDisposable()
    val unitPhraseService: UnitPhraseService by inject()

    val scopeFilter = SimpleStringProperty(vmSettings.lstScopePhraseFilters[0])
    val textFilter = SimpleStringProperty()
    val textbookFilter = SimpleObjectProperty<MSelectItem>()
    val noFilter get() = textFilter.value.isNullOrEmpty() && textbookFilter.value.value == 0

    init {
        scopeFilter.addListener { _, _, _ -> applyFilters() }
        textFilter.addListener { _, _, _ -> applyFilters() }
        textbookFilter.addListener { _, _, _ -> applyFilters() }
    }

    private fun applyFilters() =
        lstPhrases.setAll(if (noFilter) lstPhrasesAll else lstPhrasesAll.filter {
            (textFilter.value.isNullOrEmpty() || (if (scopeFilter.value == "Phrase") it.phrase else it.translation ?: "").contains(textFilter.value, true)) &&
            (textbookFilter.value.value == 0 || it.textbookid == textbookFilter.value.value)
        })

    fun reload() {
        (if (inTextbook)
            unitPhraseService.getDataByTextbookUnitPart(vmSettings.selectedTextbook, vmSettings.usunitpartfrom, vmSettings.usunitpartto)
        else
            unitPhraseService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks))
            .map { lstPhrasesAll = it.toMutableList(); applyFilters() }
            .applyIO()
            .subscribe()
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
        textbookid = vmSettings.ustextbookid
        // https://stackoverflow.com/questions/33640864/how-to-sort-based-on-compare-multiple-values-in-kotlin
        val maxItem = lstPhrasesAll.maxWithOrNull(compareBy({ it.unit }, { it.part }, { it.seqnum }))
        unit = maxItem?.unit ?: vmSettings.usunitto
        part = maxItem?.part ?: vmSettings.uspartto
        seqnum = (maxItem?.seqnum ?: 0) + 1
        textbook = vmSettings.selectedTextbook
    }

}
