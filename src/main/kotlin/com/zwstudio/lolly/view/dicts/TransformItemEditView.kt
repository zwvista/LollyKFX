package com.zwstudio.lolly.view.dicts

import com.zwstudio.lolly.data.dicts.TransformItemEditViewModel
import tornadofx.*

class TransformItemEditView : Fragment("Transform Item Edit") {
    val vmEdit : TransformItemEditViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(vmEdit.index) {
                    isEditable = false
                }
            }
            field("SEQNUM") {
                textfield(vmEdit.extractor)
            }
            field("TRANSLATION") {
                textfield(vmEdit.replacement)
            }
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
                action {
                    result = true
                    vmEdit.commit()
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
