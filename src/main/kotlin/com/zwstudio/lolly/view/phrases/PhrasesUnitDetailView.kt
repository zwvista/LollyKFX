package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.domain.wpp.UnitPhraseViewModel
import javafx.scene.control.ButtonBar
import tornadofx.*

class PhrasesUnitDetailView : Fragment("Phrases in Unit Detail") {
    val model : UnitPhraseViewModel by param()
    var result = ButtonBar.ButtonData.CANCEL_CLOSE

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

