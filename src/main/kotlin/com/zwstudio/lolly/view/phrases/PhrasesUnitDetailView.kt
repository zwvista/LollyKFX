package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.domain.wpp.UnitPhraseViewModel
import tornadofx.*

class PhrasesUnitDetailView : View("Words in Unit Detail") {
    val model : UnitPhraseViewModel by param()

    override val root = form {
        fieldset {
            field("ID") {
                textfield(model.id) {
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
    }
}

