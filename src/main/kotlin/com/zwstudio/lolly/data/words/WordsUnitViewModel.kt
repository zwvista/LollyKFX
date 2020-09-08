package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.data.BaseViewModel
import com.zwstudio.lolly.data.NoteViewModel
import com.zwstudio.lolly.data.applyIO
import com.zwstudio.lolly.domain.MSelectItem
import com.zwstudio.lolly.domain.wpp.MUnitWord
import com.zwstudio.lolly.service.UnitWordService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.asObservable

class WordsUnitViewModel(val inTextbook: Boolean) : BaseViewModel() {

    var lstWordsAll = mutableListOf<MUnitWord>()
    val lstWords = mutableListOf<MUnitWord>().asObservable()
    val vmNote: NoteViewModel by inject()
    val compositeDisposable = CompositeDisposable()
    val unitWordService: UnitWordService by inject()

    val newWord = SimpleStringProperty()
    val scopeFilter = SimpleStringProperty(vmSettings.lstScopeWordFilters[0])
    val textFilter = SimpleStringProperty()
    val textbookFilter = SimpleObjectProperty<MSelectItem>()

    init {
        scopeFilter.addListener { _, _, _ -> applyFilters() }
        textFilter.addListener { _, _, _ -> applyFilters() }
        textbookFilter.addListener { _, _, _ -> applyFilters() }
    }

    private fun applyFilters() =
        lstWords.setAll(if (textFilter.value.isNullOrEmpty() && textbookFilter.value.value == 0) lstWordsAll else lstWordsAll.filter {
            (textFilter.value.isNullOrEmpty() || (if (scopeFilter.value == "Word") it.word else it.note ?: "").contains(textFilter.value, true)) &&
            (textbookFilter.value.value == 0 || it.textbookid == textbookFilter.value.value)
        })

    fun reload() {
        (if (inTextbook)
            unitWordService.getDataByTextbookUnitPart(vmSettings.selectedTextbook, vmSettings.usunitpartfrom, vmSettings.usunitpartto)
        else
            unitWordService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks))
            .map { lstWordsAll = it.toMutableList(); applyFilters() }
            .applyIO()
            .subscribe()
        textbookFilter.value = vmSettings.lstTextbookFilters[0]
    }

    fun updateSeqNum(id: Int, seqnum: Int): Observable<Unit> =
        unitWordService.updateSeqNum(id, seqnum)
            .applyIO()

    fun update(item: MUnitWord): Observable<Unit> =
        unitWordService.update(item)
            .applyIO()

    fun create(item: MUnitWord): Observable<Int> =
        unitWordService.create(item)
            .applyIO()

    fun delete(item: MUnitWord): Observable<Unit> =
        unitWordService.delete(item)
            .applyIO()

    fun reindex(onNext: (Int) -> Unit) {
        for (i in 1..lstWordsAll.size) {
            val item = lstWordsAll[i - 1]
            if (item.seqnum == i) continue
            item.seqnum = i
            compositeDisposable.add(updateSeqNum(item.id, i).subscribe {
                onNext(i - 1)
            })
        }
    }

    fun newUnitWord() = MUnitWord().apply {
        langid = vmSettings.selectedLang.id
        textbookid = vmSettings.ustextbookid
        // https://stackoverflow.com/questions/33640864/how-to-sort-based-on-compare-multiple-values-in-kotlin
        val maxItem = lstWordsAll.maxWithOrNull(compareBy({ it.unit }, { it.part }, { it.seqnum }))
        unit = maxItem?.unit ?: vmSettings.usunitto
        part = maxItem?.part ?: vmSettings.uspartto
        seqnum = (maxItem?.seqnum ?: 0) + 1
        textbook = vmSettings.selectedTextbook
    }

    fun getNote(index: Int): Observable<Unit> {
        val item = lstWordsAll[index]
        return vmNote.getNote(item.word).flatMap {
            item.note = it
            unitWordService.updateNote(item.id, it)
        }
    }

    fun getNotes(ifEmpty: Boolean, oneComplete: (Int) -> Unit, allComplete: () -> Unit) {
        vmNote.getNotes(lstWordsAll.size, isNoteEmpty = {
            !ifEmpty || lstWordsAll[it].note.isNullOrEmpty()
        }, getOne = { i ->
            compositeDisposable.add(getNote(i).subscribe { oneComplete(i) })
        }, allComplete = allComplete)
    }
}
