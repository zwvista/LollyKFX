package com.zwstudio.lolly.data.misc

import com.zwstudio.lolly.domain.misc.MTextbook
import com.zwstudio.lolly.service.misc.TextbookService
import io.reactivex.rxjava3.core.Observable
import tornadofx.asObservable

class TextbooksViewModel : BaseViewModel() {

    var lstItems = mutableListOf<MTextbook>().asObservable()
    val textbookService: TextbookService by inject()

    init {
    }

    fun reload() {
        textbookService.getDataByLang(vmSettings.selectedLang.id)
            .map { lstItems.setAll(it) }
            .applyIO()
            .subscribe()
    }

    fun update(o: MTextbook): Observable<Unit> =
        textbookService.update(o)
        .applyIO()

    fun create(o: MTextbook): Observable<Int> =
        textbookService.create(o)
        .applyIO()

    fun delete(id: Int): Observable<Unit> =
        textbookService.delete(id)
        .applyIO()
}
