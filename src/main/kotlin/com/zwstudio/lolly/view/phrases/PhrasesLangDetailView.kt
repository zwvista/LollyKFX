package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.domain.wpp.LangPhraseViewModel
import javafx.scene.control.ButtonBar
import tornadofx.*

class PhrasesLangDetailView : Fragment("Phrases in Language Detail") {
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
        buttonbar {
            button("OK", ButtonBar.ButtonData.OK_DONE) {
                isDefaultButton = true
                action {
                    model.commit()
                    close()
                }
            }
            button("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE) {
                isCancelButton = true
                action {
                    close()
                }
            }
        }
    }
}

