package com.zwstudio.lolly.viewmodels.dicts

import com.zwstudio.lolly.models.misc.MDictionary
import com.zwstudio.lolly.services.misc.DictionaryService
import com.zwstudio.lolly.viewmodels.misc.BaseViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ListChangeListener
import tornadofx.*

class DictsViewModel : BaseViewModel() {

    var lstItems = mutableListOf<MDictionary>().asObservable()
    private val dictionaryService: DictionaryService by inject()
    val statusText = SimpleStringProperty()

    init {
        lstItems.addListener(ListChangeListener {
            statusText.value = "${lstItems.size} Dictionaries in ${vmSettings.langInfo}"
        })
    }

    fun reload() {
        dictionaryService.getDictsByLang(vmSettings.selectedLang.id)
            .applyIO()
            .subscribeBy { lstItems.setAll(it) }
    }

    fun newDictionary() = MDictionary().apply {
        langidfrom = vmSettings.selectedLang.id
        langnamefrom = vmSettings.selectedLang.langname!!
        dicttypecode = 3
    }

    fun update(o: MDictionary): Completable =
        dictionaryService.update(o)
        .applyIO()

    fun create(o: MDictionary): Single<Int> =
        dictionaryService.create(o)
        .applyIO()

    fun delete(id: Int): Completable =
        dictionaryService.delete(id)
        .applyIO()
}
