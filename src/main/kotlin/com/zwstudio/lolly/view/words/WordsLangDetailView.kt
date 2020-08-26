package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.domain.wpp.LangWordViewModel
import tornadofx.*

class WordsLangDetailView : View("Words in Unit Detail") {
    val model : LangWordViewModel by param()

    override val root = form {
        fieldset {
            field("ID") {
                textfield(model.id) {
                    isEditable = false
                }
            }
            field("WORD") {
                textfield(model.word)
            }
            field("NOTE") {
                textfield(model.note)
            }
            field("FAMIID") {
                textfield(model.famiid) {
                    isEditable = false
                }
            }
            field("LEVEL") {
                textfield(model.level)
            }
            field("ACCURACY") {
                textfield(model.accuracy) {
                    isEditable = false
                }
            }
        }
    }
}

