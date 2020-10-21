package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.data.misc.BaseViewModel
import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.domain.misc.MSelectItem
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import com.zwstudio.lolly.domain.wpp.MUnitWord
import com.zwstudio.lolly.service.wpp.UnitWordService
import com.zwstudio.lolly.service.wpp.WordPhraseService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class WordsUnitViewModel(val inTextbook: Boolean) : BaseViewModel() {

    var lstWordsAll = mutableListOf<MUnitWord>()
    val lstWords = mutableListOf<MUnitWord>().asObservable()
    val compositeDisposable = CompositeDisposable()
    val unitWordService: UnitWordService by inject()
    val lstPhrases = mutableListOf<MLangPhrase>().asObservable()
    val wordPhraseService: WordPhraseService by inject()

    val newWord = SimpleStringProperty()
    val scopeFilter = SimpleStringProperty(SettingsViewModel.lstScopeWordFilters[0])
    val textFilter = SimpleStringProperty("")
    val textbookFilter = SimpleObjectProperty<MSelectItem>()
    val noFilter get() = textFilter.value.isEmpty() && textbookFilter.value.value == 0
    val ifEmpty = SimpleBooleanProperty(true)
    val statusText = SimpleStringProperty()

    init {
        scopeFilter.addListener { _, _, _ -> applyFilters() }
        textFilter.addListener { _, _, _ -> applyFilters() }
        textbookFilter.addListener { _, _, _ -> applyFilters() }
    }

    private fun applyFilters() {
        lstWords.setAll(if (noFilter) lstWordsAll else lstWordsAll.filter {
            (textFilter.value.isEmpty() || (if (scopeFilter.value == "Word") it.word else it.note ?: "").contains(textFilter.value, true)) &&
            (textbookFilter.value.value == 0 || it.textbookid == textbookFilter.value.value)
        })
        statusText.value = "${lstWords.size} Words in ${if (inTextbook) vmSettings.unitInfo else vmSettings.langInfo}"
    }

    fun reload() {
        (if (inTextbook)
            unitWordService.getDataByTextbookUnitPart(vmSettings.selectedTextbook, vmSettings.usunitpartfrom, vmSettings.usunitpartto)
        else
            unitWordService.getDataByLang(vmSettings.selectedLang.id, vmSettings.lstTextbooks))
            .applyIO()
            .subscribe { lstWordsAll = it.toMutableList(); applyFilters() }
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
        textbookid = vmSettings.ustextbook
        // https://stackoverflow.com/questions/33640864/how-to-sort-based-on-compare-multiple-values-in-kotlin
        val maxItem = lstWordsAll.maxWithOrNull(compareBy({ it.unit }, { it.part }, { it.seqnum }))
        unit = maxItem?.unit ?: vmSettings.usunitto
        part = maxItem?.part ?: vmSettings.uspartto
        seqnum = (maxItem?.seqnum ?: 0) + 1
        textbook = vmSettings.selectedTextbook
    }

    fun retrieveNote(item: MUnitWord): Observable<Unit> {
        return vmSettings.retrieveNote(item.word).flatMap {
            item.note = it
            unitWordService.updateNote(item.id, it)
        }
    }

    fun clearNote(item: MUnitWord): Observable<Unit> {
        item.note = SettingsViewModel.zeroNote
        return unitWordService.updateNote(item.id, item.note)
    }

    fun retrieveNotes(oneComplete: (Int) -> Unit, allComplete: () -> Unit) {
        vmSettings.retrieveNotes(lstWordsAll.size, isNoteEmpty = {
            !ifEmpty.value || lstWordsAll[it].note.isNullOrEmpty()
        }, getOne = { i ->
            retrieveNote(lstWordsAll[i]).subscribe { oneComplete(i) }
        }, allComplete = allComplete)
    }

    fun clearNotes(oneComplete: (Int) -> Unit, allComplete: () -> Unit) {
        vmSettings.clearNotes(lstWordsAll.size, isNoteEmpty = {
            !ifEmpty.value || lstWordsAll[it].note.isNullOrEmpty()
        }, getOne = { i ->
            clearNote(lstWordsAll[i]).subscribe { oneComplete(i) }
        }, allComplete = allComplete)
    }

    fun getPhrases(wordid: Int?): Observable<Unit> =
        if (wordid == null)
            Observable.just(Unit)
                .doAfterNext { lstPhrases.clear() }
        else
            wordPhraseService.getPhrasesByWordId(wordid)
                .applyIO()
                .map { lstPhrases.setAll(it); Unit }
}
