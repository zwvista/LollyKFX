package com.zwstudio.lolly.views.phrases

import com.zwstudio.lolly.viewmodels.phrases.PhrasesLangDetailViewModel
import com.zwstudio.lolly.models.wpp.MUnitPhrase
import tornadofx.*

class PhrasesLangDetailView : Fragment("Phrases in Language Detail") {
    val vmDetail : PhrasesLangDetailViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(vmDetail.id) {
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

