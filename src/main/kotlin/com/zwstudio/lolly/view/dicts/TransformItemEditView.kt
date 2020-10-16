package com.zwstudio.lolly.view.dicts

import com.zwstudio.lolly.data.dicts.TransformItemEditViewModel
import tornadofx.*

class TransformItemEditView : Fragment("Transform Item Edit") {
    val vmEdit : TransformItemEditViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("Index") {
                textfield(vmEdit.index) {
                    isEditable = false
                }
            }
            field("Extractor") {
                textfield(vmEdit.extractor)
            }
            field("Replacement") {
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
