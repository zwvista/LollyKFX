package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.data.misc.BaseViewModel
import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.data.misc.extractTextFrom
import com.zwstudio.lolly.domain.misc.MReviewOptions
import com.zwstudio.lolly.domain.misc.ReviewMode
import com.zwstudio.lolly.domain.wpp.MUnitWord
import com.zwstudio.lolly.service.misc.HtmlService
import com.zwstudio.lolly.service.wpp.UnitWordService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.util.concurrent.TimeUnit

class WordsReviewViewModel : BaseViewModel() {

    val unitWordService: UnitWordService by inject()
    val vmWordFami: WordsFamiViewModel by inject()
    val htmlService: HtmlService by inject()

    var lstWords = listOf<MUnitWord>()
    val count get() = lstWords.size
    var lstCorrectIDs = mutableListOf<Int>()
    var index = 0
    val hasNext get() = index < count
    val currentItem get() = if (hasNext) lstWords[index] else null
    val currentWord get() = if (hasNext) lstWords[index].word else ""
    val options = MReviewOptions()
    val isTestMode get() = options.mode == ReviewMode.Test
    var subscriptionTimer: Disposable? = null

    val isSpeaking = SimpleBooleanProperty(true)
    val indexString = SimpleStringProperty("")
    val indexIsVisible = SimpleBooleanProperty(true)
    val correctIsVisible = SimpleBooleanProperty()
    val incorrectIsVisible = SimpleBooleanProperty()
    val accuracyString = SimpleStringProperty("")
    val accuracyIsVisible = SimpleBooleanProperty(true)
    val checkEnabled = SimpleBooleanProperty()
    val wordTargetString = SimpleStringProperty("")
    val noteTargetString = SimpleStringProperty("")
    val wordTargetIsVisible = SimpleBooleanProperty(true)
    val noteTargetIsVisible = SimpleBooleanProperty(true)
    val translationString = SimpleStringProperty("")
    val wordInputString = SimpleStringProperty("")
    val checkString = SimpleStringProperty("Check")

    fun newTest() {
        subscriptionTimer?.dispose()
        unitWordService.getDataByTextbookUnitPart(vmSettings.selectedTextbook, vmSettings.usunitpartfrom, vmSettings.usunitpartto).applyIO().subscribe {
            lstWords = it
            val nFrom = count * (options.groupSelected - 1) / options.groupCount
            val nTo = count * options.groupSelected / options.groupCount
            lstWords = lstWords.subList(nFrom, nTo)
            if (options.shuffled) lstWords = lstWords.shuffled()
            lstCorrectIDs = mutableListOf()
            index = 0
            doTest()
            checkString.value = if (isTestMode) "Check" else "Next"
            if (options.mode == ReviewMode.ReviewAuto)
                subscriptionTimer = Observable.interval(options.interval.toLong(), TimeUnit.SECONDS).applyIO().subscribe { check() }
            else
                subscriptionTimer?.dispose()
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
        val mDictTranslation = vmSettings.selectedDictTranslation
        if (mDictTranslation == null) return Observable.empty()
        val url = mDictTranslation.urlString(currentWord, vmSettings.lstAutoCorrect)
        return htmlService.getHtml(url)
            .map { extractTextFrom(it, mDictTranslation.transform!!, "") { text, _ -> text } }
            .applyIO()
    }

    fun check() {
        if (!isTestMode) {
            var b = true
            if (options.mode == ReviewMode.ReviewManual && wordInputString.value.isNotEmpty() && wordInputString.value != currentWord) {
                b = false
                incorrectIsVisible.value = true
            }
            if (b) {
                next()
                doTest()
            }
        } else if (!correctIsVisible.value && !incorrectIsVisible.value) {
            wordInputString.value = vmSettings.autoCorrectInput(wordInputString.value)
            wordTargetIsVisible.value = true
            if (wordInputString.value == currentWord)
                correctIsVisible.value = true
            else
                incorrectIsVisible.value = true
            checkString.value = "Next"
            if (!hasNext) return
            val o = currentItem!!
            val isCorrect = o.word == wordInputString.value
            if (isCorrect) lstCorrectIDs.add(o.id)
            vmWordFami.update(o.wordid, isCorrect).applyIO().subscribe {

            }
        } else {
            next()
            doTest()
            checkString.value = "Check"
        }
    }

    private fun doTest() {
        indexIsVisible.value = hasNext
        correctIsVisible.value = false
        incorrectIsVisible.value = false
        accuracyIsVisible.value = isTestMode && hasNext
        checkEnabled.value = hasNext
        wordTargetString.value = currentWord
        noteTargetString.value = currentItem?.note ?: ""
        wordTargetIsVisible.value = !isTestMode
        noteTargetIsVisible.value = !isTestMode
        translationString.value = ""
        wordInputString.value = ""
        if (hasNext) {
            indexString.value = "${index + 1}/${count}"
            accuracyString.value = currentItem!!.accuracy
            getTranslation().subscribe { translationString.value = it }
        } else if (options.mode == ReviewMode.ReviewAuto)
            subscriptionTimer?.dispose()
    }
}
