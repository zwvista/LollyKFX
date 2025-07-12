package com.zwstudio.lolly.views.words

import com.zwstudio.lolly.models.wpp.MUnitWord
import com.zwstudio.lolly.viewmodels.words.WordsUnitDetailViewModel
import tornadofx.*

class WordsTextbookDetailView : Fragment("Words in Textbook Detail") {
    val vmDetail : WordsUnitDetailViewModel by param()
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
            field("WORDID") {
                textfield(vmDetail.wordid) {
                    isEditable = false
                }
            }
            field("WORD") {
                textfield(vmDetail.word)
            }
            field("NOTE") {
                textfield(vmDetail.note)
            }
            field("FAMIID") {
                textfield(vmDetail.famiid) {
                    isEditable = false
                }
            }
            field("ACCURACY") {
                textfield(vmDetail.accuracy) {
                    isEditable = false
                }
            }
        }
        tableview(vmDetail.vmSingle.lstWords) {
            readonlyColumn("TEXTBOOKNAME", MUnitWord::textbookname)
            readonlyColumn("UNIT", MUnitWord::unitstr)
            readonlyColumn("PART", MUnitWord::partstr)
            readonlyColumn("SEQNUM", MUnitWord::seqnum)
            readonlyColumn("WORD", MUnitWord::word)
            readonlyColumn("NOTE", MUnitWord::note)
            readonlyColumn("ACCURACY", MUnitWord::accuracy)
            readonlyColumn("WORDID", MUnitWord::wordid)
            readonlyColumn("ID", MUnitWord::id)
            readonlyColumn("FAMIID", MUnitWord::famiid)
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

