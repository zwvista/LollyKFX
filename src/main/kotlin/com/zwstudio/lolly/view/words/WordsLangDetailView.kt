package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsLangDetailViewModel
import com.zwstudio.lolly.domain.wpp.MUnitWord
import tornadofx.*

class WordsLangDetailView : Fragment("Words in Language Detail") {
    val vm : WordsLangDetailViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(vm.id) {
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
        tableview(vm.vmSingle.lstWords) {
            readonlyColumn("TEXTBOOKNAME", MUnitWord::textbookname)
            readonlyColumn("UNIT", MUnitWord::unitstr)
            readonlyColumn("PART", MUnitWord::partstr)
            readonlyColumn("SEQNUM", MUnitWord::seqnum)
            readonlyColumn("WORD", MUnitWord::wordProperty)
            readonlyColumn("NOTE", MUnitWord::noteProperty)
            readonlyColumn("LEVEL", MUnitWord::level)
            readonlyColumn("ACCURACY", MUnitWord::accuracy)
            readonlyColumn("WORDID", MUnitWord::wordid)
            readonlyColumn("ID", MUnitWord::id)
            readonlyColumn("FAMIID", MUnitWord::famiid)
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

