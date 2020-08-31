package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesUnitDetailViewModel
import tornadofx.*

class PhrasesTextbookDetailView : Fragment("Phrases in Textbook Detail") {
    val model : PhrasesUnitDetailViewModel by param()
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
            field("PHRASEID") {
                textfield(model.phraseid) {
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

