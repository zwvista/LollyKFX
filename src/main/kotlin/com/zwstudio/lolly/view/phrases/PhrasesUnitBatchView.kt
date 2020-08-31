package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesUnitDetailViewModel
import tornadofx.*

class PhrasesUnitBatchView : Fragment("Phrases in Unit Batch Edit") {
    val vm : PhrasesUnitDetailViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(vm.id) {
                    isEditable = false
                }
            }
            field("UNIT") {
                combobox(vm.unititem, vm.item.textbook.lstUnits)
            }
            field("PART") {
                combobox(vm.partitem, vm.item.textbook.lstParts)
            }
            field("SEQNUM") {
                textfield(vm.seqnum)
            }
            field("PHRASEID") {
                textfield(vm.phraseid) {
                    isEditable = false
                }
            }
            field("PHRASE") {
                textfield(vm.phrase)
            }
            field("TRANSLATION") {
                textfield(vm.translation)
            }
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
                action {
                    result = true
                    vm.commit()
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

