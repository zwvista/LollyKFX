package com.zwstudio.lolly.data.words

import com.zwstudio.lolly.data.BaseViewModel
import com.zwstudio.lolly.domain.wpp.MUnitWord
import com.zwstudio.lolly.service.HtmlService
import io.reactivex.rxjava3.core.Observable
import tornadofx.asObservable

class WordsSearchViewModel : BaseViewModel() {
    var lstWords = mutableListOf<MUnitWord>().asObservable()
    var selectedWordIndex = 0
    val selectedWord: MUnitWord
        get() = lstWords[selectedWordIndex]

    lateinit var htmlService: HtmlService

    fun getHtml(url: String): Observable<String> =
        htmlService.getHtml(url)

    fun reload() {
        lstWords.clear()
    }
}
