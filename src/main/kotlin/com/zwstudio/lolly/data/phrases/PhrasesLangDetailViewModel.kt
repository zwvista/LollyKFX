package com.zwstudio.lolly.data.phrases

import com.zwstudio.lolly.domain.wpp.MLangPhrase
import tornadofx.ItemViewModel

class PhrasesLangDetailViewModel(item: MLangPhrase) : ItemViewModel<MLangPhrase>(item) {
    val id = bind(MLangPhrase::id)
    val phrase = bind(MLangPhrase::phraseProperty)
    val translation = bind(MLangPhrase::translationProperty)
    val vmSingle = SinglePhraseViewModel(item.phrase)
}
