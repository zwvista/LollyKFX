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
                combobox(values = model.item.textbook.lstUnits) {
                    selectionModel.select(model.item.textbook.lstUnits.first { it.value == model.item.unit })
                    setOnAction {
                        model.item.unit = selectionModel.selectedItem.value
                    }
                }
            }
            field("PART") {
                combobox(values = model.item.textbook.lstParts) {
                    selectionModel.select(model.item.textbook.lstParts.first { it.value == model.item.part })
                    setOnAction {
                        model.item.part = selectionModel.selectedItem.value
                    }
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

