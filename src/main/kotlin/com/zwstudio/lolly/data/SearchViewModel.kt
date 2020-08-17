package com.zwstudio.lolly.data

import com.zwstudio.lolly.service.HtmlService
import io.reactivex.rxjava3.core.Observable

class SearchViewModel : BaseViewModel2() {
    var lstWords = mutableListOf<String>()
    var selectedWordIndex = 0
    val selectedWord: String
        get() = lstWords[selectedWordIndex]

    lateinit var htmlService: HtmlService

    fun getHtml(url: String): Observable<String> =
        htmlService.getHtml(url)
}
