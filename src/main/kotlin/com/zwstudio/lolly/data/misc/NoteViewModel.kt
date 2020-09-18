package com.zwstudio.lolly.data.misc

import com.zwstudio.lolly.service.misc.HtmlService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NoteViewModel : BaseViewModel() {

    lateinit var compositeDisposable: CompositeDisposable

    val htmlService: HtmlService by inject()

    fun getNote(word: String): Observable<String> {
        val dictNote = vmSettings.selectedDictNote ?: return Observable.empty()
        val url = dictNote.urlString(word, vmSettings.lstAutoCorrect)
        return htmlService.getHtml(url).map {
            println(it)
            extractTextFrom(it, dictNote.transform!!, "") { text, _ -> text }
        }
    }

    fun getNotes(wordCount: Int, isNoteEmpty: (Int) -> Boolean, getOne: (Int) -> Unit, allComplete: () -> Unit) {
        val dictNote = vmSettings.selectedDictNote ?: return
        var i = 0
        var subscription: Disposable? = null
        subscription = Observable.interval(dictNote.wait.toLong(), TimeUnit.MILLISECONDS, Schedulers.io()).subscribe {
            while (i < wordCount && !isNoteEmpty(i))
                i++
            if (i > wordCount) {
                allComplete()
                subscription?.dispose()
            } else {
                if (i < wordCount)
                    getOne(i)
                i++
            }
        }
        compositeDisposable.add(subscription)
    }
}