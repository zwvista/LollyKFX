package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.domain.wpp.UnitWordViewModel
import javafx.scene.control.ButtonBar
import tornadofx.*

class WordsTextbookDetailView : Fragment("Words in Textbook Detail") {
    val model : UnitWordViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(model.id) {
                    isEditable = false
                }
            }
            field("TEXTBOOK") {
                textfield(model.textbookname) {
                    isEditable = false
                }
            }
            field("UNIT") {
                combobox(model.unititem, model.item.textbook.lstUnits)
            }
            field("PART") {
                combobox(model.partitem, model.item.textbook.lstParts)
            }
            field("SEQNUM") {
                textfield(model.seqnum)
            }
            field("WORDID") {
                textfield(model.wordid) {
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

