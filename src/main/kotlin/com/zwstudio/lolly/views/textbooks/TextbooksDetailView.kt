package com.zwstudio.lolly.views.textbooks

import com.zwstudio.lolly.viewmodels.textbooks.TextbooksDetailViewModel
import tornadofx.*

class TextbooksDetailView : Fragment("Textbooks Detail") {
    val vmDetail : TextbooksDetailViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(vmDetail.id) {
                    isEditable = false
                }
            }
            field("LANGUAGE") {
                textfield(vmDetail.langname) {
                    isEditable = false
                }
            }
            field("TEXTBOOKNAME") {
                textfield(vmDetail.textbookname)
            }
            field("UNITS") {
                textfield(vmDetail.units)
            }
            field("PARTS") {
                textfield(vmDetail.parts)
            }
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

