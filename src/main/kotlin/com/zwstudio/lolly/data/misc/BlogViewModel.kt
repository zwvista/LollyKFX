package com.zwstudio.lolly.data.misc

import com.zwstudio.lolly.service.misc.BlogService
import javafx.beans.property.SimpleStringProperty

class BlogViewModel : BaseViewModel() {
    val blogService: BlogService by inject()

    val markedText = SimpleStringProperty()
    val htmlText = SimpleStringProperty()
    val patternNo = SimpleStringProperty("001")
    val patternText = SimpleStringProperty()
    val patterUrl get() = blogService.getPatternUrl(patternNo.value)
    val patternMarkDown get() = blogService.getPatternMarkDown((patternText.value))

    init {
    }

    fun htmlToMarked() {
        markedText.value = blogService.htmlToMarked(htmlText.value)
    }
    fun addTagB(text: String) = blogService.addTagB(text)
    fun addTagI(text: String) = blogService.addTagI(text)
    fun removeTagBI(text: String) = blogService.removeTagBI(text)
    fun exchangeTagBI(text: String) = blogService.exchangeTagBI(text)
    fun getExplanation(text: String) = blogService.getExplanation(text)
    fun markedToHtml(): String {
        htmlText.value = blogService.markedToHtml(markedText.value)
        val str = toHtml(htmlText.value)
        return str
    }
}
