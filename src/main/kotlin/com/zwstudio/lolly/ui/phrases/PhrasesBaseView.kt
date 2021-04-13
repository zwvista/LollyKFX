package com.zwstudio.lolly.ui.phrases

import com.zwstudio.lolly.viewmodels.words.WordsLangViewModel
import com.zwstudio.lolly.models.wpp.MLangWord
import com.zwstudio.lolly.ui.words.WordsPhraseBaseView
import javafx.scene.Node
import javafx.scene.control.TableView
import tornadofx.*

abstract class PhrasesBaseView(title: String? = null, icon: Node? = null) : WordsPhraseBaseView(title, icon) {
    var tvWords: TableView<MLangWord> by singleAssign()
    var vmWordsLang = WordsLangViewModel()

}
