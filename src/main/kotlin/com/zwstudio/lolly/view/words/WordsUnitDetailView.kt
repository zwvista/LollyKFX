package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.domain.UnitWordViewModel
import tornadofx.*

class WordsUnitDetailView : View("Words in Unit Detail") {
    val model : UnitWordViewModel by param()

    override val root = form {
        fieldset {
            field("WORD") {
                textfield(model.word)
            }
        }
    }

}

