package com.zwstudio.lolly.viewmodels.phrases

import com.zwstudio.lolly.models.wpp.MLangPhrase
import tornadofx.ItemViewModel

class PhrasesLangDetailViewModel(val vm: PhrasesLangViewModel, item: MLangPhrase) : ItemViewModel<MLangPhrase>(item) {
    val id = bind(MLangPhrase::id)
    val phrase = bind(MLangPhrase::phraseProperty)
    val translation = bind(MLangPhrase::translationProperty)
    val vmSingle = SinglePhraseViewModel(item.phrase)

    override fun onCommit() {
        super.onCommit()
        if (item.id == 0)
            vm.create(item).subscribe()
        else
            vm.update(item).subscribe()
    }
}
