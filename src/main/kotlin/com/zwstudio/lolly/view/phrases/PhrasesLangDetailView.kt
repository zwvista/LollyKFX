package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.domain.wpp.LangPhraseViewModel
import tornadofx.*

class PhrasesLangDetailView : View("Words in Unit Detail") {
    val model : LangPhraseViewModel by param()

    override val root = form {
        fieldset {
            field("ID") {
                textfield(model.id) {
                    isEditable = false
                }
            }
            field("PHRASE") {
                textfield(model.phrase)
            }
            field("TRANSLATION") {
                textfield(model.translation)
            }
        }
    }
}

