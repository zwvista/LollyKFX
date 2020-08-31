package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesLangDetailViewModel
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import tornadofx.*

class PhrasesLangDetailView : Fragment("Phrases in Language Detail") {
    val model : PhrasesLangDetailViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(model.id) {
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
        tableview(model.vmSingle.lstPhrases) {
            readonlyColumn("UNIT", MUnitPhrase::unitstr)
            readonlyColumn("PART", MUnitPhrase::partstr)
            readonlyColumn("SEQNUM", MUnitPhrase::seqnum)
            readonlyColumn("PHRASE", MUnitPhrase::phraseProperty)
            readonlyColumn("TRANSLATION", MUnitPhrase::translationProperty)
            readonlyColumn("PHRASEID", MUnitPhrase::phraseid)
            readonlyColumn("ID", MUnitPhrase::id)
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
                action {
                    result = true
                    model.commit()
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

