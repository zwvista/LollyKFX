package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsUnitDetailViewModel
import tornadofx.*

class WordsTextbookDetailView : Fragment("Words in Textbook Detail") {
    val vm : WordsUnitDetailViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(vm.id) {
                    isEditable = false
                }
            }
            field("TEXTBOOK") {
                textfield(vm.textbookname) {
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
            field("WORDID") {
                textfield(vm.wordid) {
                    isEditable = false
                }
            }
            field("WORD") {
                textfield(vm.word)
            }
            field("NOTE") {
                textfield(vm.note)
            }
            field("FAMIID") {
                textfield(vm.famiid) {
                    isEditable = false
                }
            }
            field("LEVEL") {
                textfield(vm.level)
            }
            field("ACCURACY") {
                textfield(vm.accuracy) {
                    isEditable = false
                }
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

