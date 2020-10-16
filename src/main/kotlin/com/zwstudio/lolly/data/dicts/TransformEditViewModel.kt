package com.zwstudio.lolly.data.dicts

import com.zwstudio.lolly.data.misc.applyTemplate
import com.zwstudio.lolly.data.misc.doTransform
import com.zwstudio.lolly.data.misc.toHtml
import com.zwstudio.lolly.data.misc.toTransformItems
import io.reactivex.rxjava3.core.Observable
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class TransformEditViewModel(val vmDetail: DictsDetailViewModel) : ViewModel() {
    val sourceWord = SimpleStringProperty("")
    var sourceUrl = ""
    val sourceText = SimpleStringProperty("")
    val resultText = SimpleStringProperty("")
    var resultHtml = ""
    val interimText = SimpleStringProperty("")
    var interimMaxIndex = 0
    val interimIndex = SimpleObjectProperty<Int>()
    val lstTranformItems = toTransformItems(vmDetail.transform.value).asObservable()
    var interimResults = mutableListOf<String>()

    init {
        interimIndex.addListener { _, _, newValue ->
            interimText.value = interimResults[newValue]
        }
    }

    override fun onCommit() {
        super.onCommit()
        vmDetail.transform.value = lstTranformItems.flatMap { listOf(it.extractor, it.replacement) }.joinToString("\n")
    }

    fun getHtml(): Observable<Unit> {
        sourceUrl = vmDetail.url.value.replace("{0}", sourceWord.value)
        return vmDetail.vm.vmSettings.getHtml(sourceUrl).map { sourceText.value = it }
    }

    fun transformText() {
        var text = sourceText.value
        interimResults = mutableListOf(text)
        for (item in lstTranformItems) {
            text = doTransform(text, item)
            interimResults.add(text)
        }
        interimIndex.value = 0
        interimMaxIndex = interimResults.size - 1
        resultText.value = text
        val t = vmDetail.template.value
        resultHtml = if (t.isEmpty()) toHtml(text) else applyTemplate(t, sourceWord.value, text)
    }

}
