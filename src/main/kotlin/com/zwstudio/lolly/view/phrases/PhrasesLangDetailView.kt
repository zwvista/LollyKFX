package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.domain.wpp.LangPhraseViewModel
import tornadofx.*

class PhrasesLangDetailView : Fragment("Phrases in Language Detail") {
    val model : LangPhraseViewModel by param()
    var result = false

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
        buttonbar {
            button("OK") {
                isDefaultButton = true
                action {
                    result = true
                    model.commit()
                    close()
                }
            }
            button("Cancel") {
                isCancelButton = true
                action {
                    close()
                }
            }
        }
    }
}

