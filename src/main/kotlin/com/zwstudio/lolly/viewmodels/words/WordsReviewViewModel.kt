package com.zwstudio.lolly.viewmodels.words

import com.zwstudio.lolly.models.misc.MReviewOptions
import com.zwstudio.lolly.models.misc.ReviewMode
import com.zwstudio.lolly.models.wpp.MUnitWord
import com.zwstudio.lolly.services.wpp.UnitWordService
import com.zwstudio.lolly.services.wpp.WordFamiService
import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import com.zwstudio.lolly.viewmodels.misc.extractTextFrom
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.util.concurrent.TimeUnit
import kotlin.math.min

class WordsReviewViewModel : BaseViewModel() {

    private val unitWordService: UnitWordService by inject()
    private val wordFamiService: WordFamiService by inject()

    var lstWords = listOf<MUnitWord>()
    val count get() = lstWords.size
    var lstCorrectIDs = mutableListOf<Int>()
    var index = 0
    val hasNext get() = index < count
    val currentItem get() = if (hasNext) lstWords[index] else null
    val currentWord get() = if (hasNext) lstWords[index].word else ""
    val options = MReviewOptions()
    val isTestMode get() = options.mode == ReviewMode.Test || options.mode == ReviewMode.Textbook
    var subscriptionTimer: Disposable? = null

    val isSpeaking = SimpleBooleanProperty(true)
    val indexString = SimpleStringProperty("")
    val indexVisible = SimpleBooleanProperty(true)
    val correctVisible = SimpleBooleanProperty()
    val incorrectVisible = SimpleBooleanProperty()
    val accuracyString = SimpleStringProperty("")
    val accuracyVisible = SimpleBooleanProperty(true)
    val checkEnabled = SimpleBooleanProperty()
    val wordTargetString = SimpleStringProperty("")
    val noteTargetString = SimpleStringProperty("")
    val wordHintString = SimpleStringProperty("")
    val wordTargetVisible = SimpleBooleanProperty(true)
    val noteTargetVisible = SimpleBooleanProperty(true)
    val wordHintVisible = SimpleBooleanProperty(true)
    val translationString = SimpleStringProperty("")
    val wordInputString = SimpleStringProperty("")
    val checkString = SimpleStringProperty("Check")
    val searchEnabled = SimpleBooleanProperty()

    fun newTest() {
        fun f() {
            lstCorrectIDs = mutableListOf()
            index = 0
            doTest()
            checkString.value = if (isTestMode) "Check" else "Next"
        }
        subscriptionTimer?.dispose()
        if (options.mode == ReviewMode.Textbook)
            unitWordService.getDataByTextbook(vmSettings.selectedTextbook).applyIO().subscribe {
                val lst2 = mutableListOf<MUnitWord>()
                for (o in it) {
                    val s = o.accuracy
                    val percentage = if (!s.endsWith("%")) 0.0 else s.trimEnd('%').toDouble()
                    val t = 6 - (percentage / 20.0).toInt()
                    for (i in 0 until t)
                        lst2.add(o)
                }
                val lst3 = mutableListOf<MUnitWord>()
                val cnt = min(options.reviewCount, it.size)
                while (lst3.size < cnt) {
                    val o = lst2.random()
                    if (!lst3.contains(o))
                        lst3.add(o)
                }
                lstWords = lst3.toList()
                f()
            }
        else
            unitWordService.getDataByTextbookUnitPart(vmSettings.selectedTextbook, vmSettings.usunitpartfrom, vmSettings.usunitpartto).applyIO().subscribe {
                lstWords = it
                val nFrom = count * (options.groupSelected - 1) / options.groupCount
                val nTo = count * options.groupSelected / options.groupCount
                lstWords = lstWords.subList(nFrom, nTo)
                if (options.shuffled) lstWords = lstWords.shuffled()
                f()
                if (options.mode == ReviewMode.ReviewAuto)
                    subscriptionTimer = Observable.interval(options.interval.toLong(), TimeUnit.SECONDS).applyIO().subscribe { check() }
            }
    }

    fun next() {
        index++
        if (isTestMode && !hasNext) {
            index = 0
            lstWords = lstWords.filter { !lstCorrectIDs.contains(it.id) }
        }
    }

    private fun getTranslation(): Observable<String> {
        val dictTranslation = vmSettings.selectedDictTranslation ?: return Observable.empty()
        val url = dictTranslation.urlString(currentWord, vmSettings.lstAutoCorrect)
        return vmSettings.getHtml(url)
            .map { extractTextFrom(it, dictTranslation.transform, "") { text, _ -> text } }
            .applyIO()
    }

    fun check() {
        if (!isTestMode) {
            var b = true
            if (options.mode == ReviewMode.ReviewManual && wordInputString.value.isNotEmpty() && wordInputString.value != currentWord) {
                b = false
                incorrectVisible.value = true
            }
            if (b) {
                next()
                doTest()
            }
        } else if (!correctVisible.value && !incorrectVisible.value) {
            wordInputString.value = vmSettings.autoCorrectInput(wordInputString.value)
            wordTargetVisible.value = true
            if (wordInputString.value == currentWord)
                correctVisible.value = true
            else
                incorrectVisible.value = true
            wordHintVisible.value = false
            searchEnabled.value = true
            checkString.value = "Next"
            if (!hasNext) return
            val o = currentItem!!
            val isCorrect = o.word == wordInputString.value
            if (isCorrect) lstCorrectIDs.add(o.id)
            wordFamiService.update(o.wordid, isCorrect).applyIO().subscribe {
                o.correct = it.correct
                o.total = it.total
                accuracyString.value = o.accuracy
            }
        } else {
            next()
            doTest()
            checkString.value = "Check"
        }
    }

    private fun doTest() {
        indexVisible.value = hasNext
        correctVisible.value = false
        incorrectVisible.value = false
        accuracyVisible.value = isTestMode && hasNext
        checkEnabled.value = hasNext
        wordTargetString.value = currentWord
        noteTargetString.value = currentItem?.note ?: ""
        wordTargetVisible.value = !isTestMode
        noteTargetVisible.value = !isTestMode
        wordHintString.value = currentItem?.word?.length?.toString() ?: ""
        wordHintVisible.value = isTestMode
        translationString.value = ""
        wordInputString.value = ""
        searchEnabled.value = false
        if (hasNext) {
            indexString.value = "${index + 1}/$count"
            accuracyString.value = currentItem!!.accuracy
            getTranslation().subscribe {
                translationString.value = it
                if (it.isEmpty() && !options.speakingEnabled)
                    wordInputString.value = currentWord
            }
        } else if (options.mode == ReviewMode.ReviewAuto)
            subscriptionTimer?.dispose()
    }
}
