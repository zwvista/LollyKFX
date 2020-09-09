package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.data.misc.BaseViewModel
import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.domain.misc.MReviewOptions
import com.zwstudio.lolly.domain.misc.ReviewMode
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import com.zwstudio.lolly.service.UnitPhraseService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import java.util.concurrent.TimeUnit

class PhrasesReviewViewModel : BaseViewModel() {

    val unitPhraseService: UnitPhraseService by inject()

    var lstPhrases = listOf<MUnitPhrase>()
    val count get() = lstPhrases.size
    var lstCorrectIDs = mutableListOf<Int>()
    var index = 0
    val hasNext get() = index < count
    val currentItem get() = if (hasNext) lstPhrases[index] else null
    val currentPhrase get() = if (hasNext) lstPhrases[index].phrase else ""
    val options = MReviewOptions()
    val isTestMode get() = options.mode == ReviewMode.Test
    var subscriptionTimer: Disposable? = null

    val isSpeaking = SimpleBooleanProperty(true)
    val indexString = SimpleStringProperty("")
    val indexIsVisible = SimpleBooleanProperty(true)
    val correctIsVisible = SimpleBooleanProperty()
    val incorrectIsVisible = SimpleBooleanProperty()
    val checkEnabled = SimpleBooleanProperty()
    val phraseTargetString = SimpleStringProperty("")
    val phraseTargetIsVisible = SimpleBooleanProperty(true)
    val translationString = SimpleStringProperty("")
    val phraseInputString = SimpleStringProperty("")
    val checkString = SimpleStringProperty("Check")

    fun newTest() {
        unitPhraseService.getDataByTextbookUnitPart(vmSettings.selectedTextbook, vmSettings.usunitpartfrom, vmSettings.usunitpartto).applyIO().subscribe {
            lstPhrases = it
            val nFrom = count * (options.groupSelected - 1) / options.groupCount
            val nTo = count * options.groupSelected / options.groupCount
            lstPhrases = lstPhrases.subList(nFrom, nTo)
            if (options.shuffled) lstPhrases = lstPhrases.shuffled()
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
            lstPhrases = lstPhrases.filter { !lstCorrectIDs.contains(it.id) }
        }
    }

    fun check() {
        if (!isTestMode) {
            var b = true
            if (options.mode == ReviewMode.ReviewManual && phraseInputString.value.isNotEmpty() && phraseInputString.value != currentPhrase) {
                b = false
                incorrectIsVisible.value = true
            }
            if (b) {
                next()
                doTest()
            }
        } else if (!correctIsVisible.value && !incorrectIsVisible.value) {
            phraseInputString.value = vmSettings.autoCorrectInput(phraseInputString.value)
            phraseTargetIsVisible.value = true
            if (phraseInputString.value == currentPhrase)
                correctIsVisible.value = true
            else
                incorrectIsVisible.value = true
            checkString.value = "Next"
            if (!hasNext) return
            val o = currentItem!!
            val isCorrect = o.phrase == phraseInputString.value
            if (isCorrect) lstCorrectIDs.add(o.id)
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
        checkEnabled.value = hasNext
        phraseTargetString.value = currentPhrase
        translationString.value = currentItem?.translation ?: ""
        phraseTargetIsVisible.value = !isTestMode
        phraseInputString.value = ""
        if (hasNext)
            indexString.value = "${index + 1}/${count}"
        else if (options.mode == ReviewMode.ReviewAuto)
            subscriptionTimer?.dispose()
    }
}
