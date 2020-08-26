package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.domain.wpp.LangWordViewModel
import javafx.scene.control.ButtonBar
import tornadofx.*

class WordsLangDetailView : Fragment("Words in Language Detail") {
    val model : LangWordViewModel by param()
    var result = ButtonBar.ButtonData.CANCEL_CLOSE

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
        buttonbar {
            button("OK", ButtonBar.ButtonData.OK_DONE) {
                isDefaultButton = true
                action {
                    result = ButtonBar.ButtonData.OK_DONE
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

