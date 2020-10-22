package com.zwstudio.lolly.view.patterns

import com.zwstudio.lolly.data.patterns.PatternsDetailViewModel
import tornadofx.*

class PatternsDetailView : Fragment("Patterns in Language Detail") {
    val vmDetail : PatternsDetailViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(vmDetail.id) {
                    isEditable = false
                }
            }
            field("PATTERN") {
                textfield(vmDetail.pattern)
            }
            field("NOTE") {
                textfield(vmDetail.note)
            }
            field("TAGS") {
                textfield(vmDetail.tags)
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

