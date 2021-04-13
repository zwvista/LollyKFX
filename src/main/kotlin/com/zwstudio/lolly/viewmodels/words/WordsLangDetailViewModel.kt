package com.zwstudio.lolly.viewmodels.words

import com.zwstudio.lolly.models.wpp.MLangWord
import tornadofx.*

class WordsLangDetailViewModel(val vm: WordsLangViewModel, item: MLangWord) : ItemViewModel<MLangWord>(item) {
    val id = bind(MLangWord::id)
    val word = bind(MLangWord::wordProperty)
    val note = bind(MLangWord::noteProperty)
    val famiid = bind(MLangWord::famiid)
    val accuracy = bind(MLangWord::accuracy)
    val vmSingle = SingleWordViewModel(item.word)

    override fun onCommit() {
        super.onCommit()
        if (item.id == 0)
            vm.create(item).subscribe()
        else
            vm.update(item).subscribe()
    }
}
