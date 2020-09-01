package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesUnitDetailViewModel
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import tornadofx.*

class PhrasesUnitDetailView : Fragment("Phrases in Unit Detail") {
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
                combobox(vm.unititem, vm.item.textbook.lstUnits) {
                    maxWidth = Double.MAX_VALUE
                }
            }
            field("PART") {
                combobox(vm.partitem, vm.item.textbook.lstParts) {
                    maxWidth = Double.MAX_VALUE
                }
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
        tableview(vm.vmSingle.lstPhrases) {
            readonlyColumn("TEXTBOOKNAME", MUnitPhrase::textbookname)
            readonlyColumn("UNIT", MUnitPhrase::unitstr)
            readonlyColumn("PART", MUnitPhrase::partstr)
            readonlyColumn("SEQNUM", MUnitPhrase::seqnum)
            readonlyColumn("PHRASE", MUnitPhrase::phrase)
            readonlyColumn("TRANSLATION", MUnitPhrase::translation)
            readonlyColumn("PHRASEID", MUnitPhrase::phraseid)
            readonlyColumn("ID", MUnitPhrase::id)
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

