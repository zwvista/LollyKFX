package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.domain.UnitWordViewModel
import tornadofx.*

class WordsUnitDetailView : View("Words in Unit Detail") {
    val model : UnitWordViewModel by param()

    override val root = form {
        fieldset {
            field("ID") {
                textfield(model.id) {
                    isEditable = false
                }
            }
            field("UNIT") {
                choicebox(model.unit) {
                }
            }
            field("WORD") {
                textfield(model.word)
            }
            field("NOTE") {
                textfield(model.note)
            }
        }
    }
}

