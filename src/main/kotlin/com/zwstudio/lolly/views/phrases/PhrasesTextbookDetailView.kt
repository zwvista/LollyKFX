package com.zwstudio.lolly.views.phrases

import com.zwstudio.lolly.models.wpp.MUnitPhrase
import com.zwstudio.lolly.viewmodels.phrases.PhrasesUnitDetailViewModel
import tornadofx.*

class PhrasesTextbookDetailView : Fragment("Phrases in Textbook Detail") {
    val vmDetail : PhrasesUnitDetailViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(vmDetail.id) {
                    isEditable = false
                }
            }
            field("TEXTBOOK") {
                textfield(vmDetail.textbookname) {
                    isEditable = false
                }
            }
            field("UNIT") {
                combobox(vmDetail.unititem, vmDetail.item.textbook.lstUnits) {
                    maxWidth = Double.MAX_VALUE
                }
            }
            field("PART") {
                combobox(vmDetail.partitem, vmDetail.item.textbook.lstParts) {
                    maxWidth = Double.MAX_VALUE
                }
            }
            field("SEQNUM") {
                textfield(vmDetail.seqnum)
            }
            field("PHRASEID") {
                textfield(vmDetail.phraseid) {
                    isEditable = false
                }
            }
            field("PHRASE") {
                textfield(vmDetail.phrase)
            }
            field("TRANSLATION") {
                textfield(vmDetail.translation)
            }
        }
        tableview(vmDetail.vmSingle.lstPhrases) {
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
            }.action {
                result = true
                vmDetail.commit()
                close()
            }
            button("Cancel") {
                isCancelButton = true
            }.action {
                close()
            }
        }
    }
}

