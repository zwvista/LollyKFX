package com.zwstudio.lolly.viewmodels.words

import com.zwstudio.lolly.common.extractTextFrom
import com.zwstudio.lolly.models.misc.MReviewOptions
import com.zwstudio.lolly.models.misc.ReviewMode
import com.zwstudio.lolly.models.wpp.MUnitWord
import com.zwstudio.lolly.services.wpp.UnitWordService
import com.zwstudio.lolly.services.wpp.WordFamiService
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.util.concurrent.TimeUnit
import kotlin.math.min

class WordsReviewViewModel(private val doTestAction: WordsReviewViewModel.() -> Unit) : WordsBaseViewModel() {

    private val unitWordService: UnitWordService by inject()
    private val wordFamiService: WordFamiService by inject()

    var lstWords = listOf<MUnitWord>()
    val count get() = lstWords.size
    var lstCorrectIDs = mutableListOf<Int>()
    var index = 0
    val hasCurrent get() = lstWords.isNotEmpty() && (onRepeat.value == true || index in 0 until count)
    val currentItem get() = if (hasCurrent) lstWords[index] else null
    val currentWord get() = if (hasCurrent) lstWords[index].word else ""
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
    val checkNextEnabled = SimpleBooleanProperty(true)
    val checkNextString = SimpleStringProperty("Check")
    val checkPrevEnabled = SimpleBooleanProperty(true)
    val checkPrevString = SimpleStringProperty("Check")
    val checkPrevVisible = SimpleBooleanProperty(true)
    val wordTargetString = SimpleStringProperty("")
    val noteTargetString = SimpleStringProperty("")
    val wordHintString = SimpleStringProperty("")
    val wordTargetVisible = SimpleBooleanProperty(true)
    val noteTargetVisible = SimpleBooleanProperty(true)
    val wordHintVisible = SimpleBooleanProperty(true)
    val translationString = SimpleStringProperty("")
    val wordInputString = SimpleStringProperty("")
    val onRepeat = SimpleBooleanProperty(true)
    val moveForward = SimpleBooleanProperty(true)
    val onRepeatVisible = SimpleBooleanProperty(true)
    val moveForwardVisible = SimpleBooleanProperty(true)

    fun newTest() {
        fun f() {
            index = if (moveForward.value!!) 0 else count - 1
            doTest()
            checkNextString.value = if (isTestMode) "Check" else "Next"
            checkPrevString.value = if (isTestMode) "Check" else "Prev"
        }
        lstWords = listOf()
        lstCorrectIDs = mutableListOf()
        index = 0
        subscriptionTimer?.dispose()
        isSpeaking.value = options.speakingEnabled
        moveForward.value = options.moveForward
        moveForwardVisible.value = !isTestMode
        onRepeat.value = !isTestMode && options.onRepeat
        onRepeatVisible.value = !isTestMode
        checkPrevVisible.value = !isTestMode
        if (options.mode == ReviewMode.Textbook)
            unitWordService.getDataByTextbook(vmSettings.selectedTextbook).applyIO().subscribeBy {
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
            unitWordService.getDataByTextbookUnitPart(vmSettings.selectedTextbook, vmSettings.usunitpartfrom, vmSettings.usunitpartto).applyIO().subscribeBy {
                lstWords = it
                val nFrom = count * (options.groupSelected - 1) / options.groupCount
                val nTo = count * options.groupSelected / options.groupCount
                lstWords = lstWords.subList(nFrom, nTo)
                if (options.shuffled) lstWords = lstWords.shuffled()
                f()
                if (options.mode == ReviewMode.ReviewAuto)
                    subscriptionTimer = Observable.interval(options.interval.toLong(), TimeUnit.SECONDS).applyIO().subscribe { check(true) }
            }
    }

    fun move(toNext: Boolean) {
        fun checkOnRepeat() {
            if (onRepeat.value!!) {
                index = (index + count) % count
            }
        }
        if (moveForward.value == toNext) {
            index++
            checkOnRepeat()
            if (isTestMode && !hasCurrent) {
                index = 0
                lstWords = lstWords.filter { !lstCorrectIDs.contains(it.id) }
            }
        } else {
            index--
            checkOnRepeat()
        }
    }

    private fun getTranslation(): Single<String> {
        val dictTranslation = vmSettings.selectedDictTranslation ?: return Single.just("")
        val url = dictTranslation.urlString(currentWord, vmSettings.lstAutoCorrect)
        return vmSettings.getHtml(url)
            .map { extractTextFrom(it, dictTranslation.transform, "") { text, _ -> text } }
            .applyIO()
    }

    fun check(toNext: Boolean) {
        if (!isTestMode) {
            var b = true
            if (options.mode == ReviewMode.ReviewManual && wordInputString.value.isNotEmpty() && wordInputString.value != currentWord) {
                b = false
                incorrectVisible.value = true
            }
            if (b) {
                move(toNext)
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
            checkNextString.value = "Next"
            checkPrevString.value = "Prev"
            if (!hasCurrent) return
            val o = currentItem!!
            val isCorrect = o.word == wordInputString.value
            if (isCorrect) lstCorrectIDs.add(o.id)
            wordFamiService.update(o.wordid, isCorrect).applyIO().subscribeBy {
                o.correct = it.correct
                o.total = it.total
                accuracyString.value = o.accuracy
            }
        } else {
            move(toNext)
            doTest()
            checkNextString.value = "Check"
            checkPrevString.value = "Check"
        }
    }

    private fun doTest() {
        indexVisible.value = hasCurrent
        correctVisible.value = false
        incorrectVisible.value = false
        accuracyVisible.value = isTestMode && hasCurrent
        checkNextEnabled.value = hasCurrent
        checkPrevEnabled.value = hasCurrent
        wordTargetString.value = currentWord
        noteTargetString.value = currentItem?.note ?: ""
        wordTargetVisible.value = !isTestMode
        noteTargetVisible.value = !isTestMode
        wordHintString.value = currentItem?.word?.length?.toString() ?: ""
        wordHintVisible.value = isTestMode
        translationString.value = ""
        wordInputString.value = ""
        doTestAction()
        if (hasCurrent) {
            indexString.value = "${index + 1}/$count"
            accuracyString.value = currentItem!!.accuracy
            getTranslation().subscribeBy {
                translationString.value = it
                if (it.isEmpty() && !options.speakingEnabled)
                    wordInputString.value = currentWord
            }
        } else if (options.mode == ReviewMode.ReviewAuto)
            subscriptionTimer?.dispose()
    }
}
