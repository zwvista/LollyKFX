package com.zwstudio.lolly.viewmodels.phrases

import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import com.zwstudio.lolly.models.misc.MReviewOptions
import com.zwstudio.lolly.models.misc.ReviewMode
import com.zwstudio.lolly.models.wpp.MUnitPhrase
import com.zwstudio.lolly.services.wpp.UnitPhraseService
import com.zwstudio.lolly.viewmodels.words.WordsReviewViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.util.concurrent.TimeUnit
import kotlin.math.min

class PhrasesReviewViewModel(private val doTestAction: PhrasesReviewViewModel.() -> Unit) : BaseViewModel() {

    private val unitPhraseService: UnitPhraseService by inject()

    var lstPhrases = listOf<MUnitPhrase>()
    val count get() = lstPhrases.size
    var lstCorrectIDs = mutableListOf<Int>()
    var index = 0
    val hasCurrent get() = index < count
    val currentItem get() = if (hasCurrent) lstPhrases[index] else null
    val currentPhrase get() = if (hasCurrent) lstPhrases[index].phrase else ""
    val options = MReviewOptions()
    val isTestMode get() = options.mode == ReviewMode.Test || options.mode == ReviewMode.Textbook
    var subscriptionTimer: Disposable? = null

    val isSpeaking = SimpleBooleanProperty(true)
    val indexString = SimpleStringProperty("")
    val indexVisible = SimpleBooleanProperty(true)
    val correctVisible = SimpleBooleanProperty()
    val incorrectVisible = SimpleBooleanProperty()
    val checkNextEnabled = SimpleBooleanProperty(true)
    val checkNextString = SimpleStringProperty("Check")
    val checkPrevEnabled = SimpleBooleanProperty(true)
    val checkPrevString = SimpleStringProperty("Check")
    val checkPrevVisible = SimpleBooleanProperty(true)
    val phraseTargetString = SimpleStringProperty("")
    val phraseTargetVisible = SimpleBooleanProperty(true)
    val translationString = SimpleStringProperty("")
    val phraseInputString = SimpleStringProperty("")
    val onRepeat = SimpleBooleanProperty(true)
    val moveForward = SimpleBooleanProperty(true)
    val onRepeatVisible = SimpleBooleanProperty(true)
    val moveForwardVisible = SimpleBooleanProperty(true)

    fun newTest() {
        fun f() {
            lstCorrectIDs = mutableListOf()
            index = 0
            doTest()
            checkNextString.value = if (isTestMode) "Check" else "Next"
            checkPrevString.value = if (isTestMode) "Check" else "Prev"
        }
        subscriptionTimer?.dispose()
        if (options.mode == ReviewMode.Textbook)
            unitPhraseService.getDataByTextbook(vmSettings.selectedTextbook).applyIO().subscribe {
                val cnt = min(options.reviewCount, it.size)
                lstPhrases = it.shuffled().subList(0, cnt)
                f()
            }
        else
            unitPhraseService.getDataByTextbookUnitPart(vmSettings.selectedTextbook, vmSettings.usunitpartfrom, vmSettings.usunitpartto).applyIO().subscribe {
                lstPhrases = it
                val nFrom = count * (options.groupSelected - 1) / options.groupCount
                val nTo = count * options.groupSelected / options.groupCount
                lstPhrases = lstPhrases.subList(nFrom, nTo)
                if (options.shuffled) lstPhrases = lstPhrases.shuffled()
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
                lstPhrases = lstPhrases.filter { !lstCorrectIDs.contains(it.id) }
            }
        } else {
            index--
            checkOnRepeat()
        }
    }

    fun check(toNext: Boolean) {
        if (!isTestMode) {
            var b = true
            if (options.mode == ReviewMode.ReviewManual && phraseInputString.value.isNotEmpty() && phraseInputString.value != currentPhrase) {
                b = false
                incorrectVisible.value = true
            }
            if (b) {
                move(toNext)
                doTest()
            }
        } else if (!correctVisible.value && !incorrectVisible.value) {
            phraseInputString.value = vmSettings.autoCorrectInput(phraseInputString.value)
            phraseTargetVisible.value = true
            if (phraseInputString.value == currentPhrase)
                correctVisible.value = true
            else
                incorrectVisible.value = true
            checkNextString.value = "Next"
            checkPrevString.value = "Prev"
            if (!hasCurrent) return
            val o = currentItem!!
            val isCorrect = o.phrase == phraseInputString.value
            if (isCorrect) lstCorrectIDs.add(o.id)
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
        checkNextEnabled.value = hasCurrent
        checkPrevEnabled.value = hasCurrent
        phraseTargetString.value = currentPhrase
        translationString.value = currentItem?.translation ?: ""
        phraseTargetVisible.value = !isTestMode
        phraseInputString.value = ""
        doTestAction()
        if (hasCurrent)
            indexString.value = "${index + 1}/$count"
        else if (options.mode == ReviewMode.ReviewAuto)
            subscriptionTimer?.dispose()
    }
}
