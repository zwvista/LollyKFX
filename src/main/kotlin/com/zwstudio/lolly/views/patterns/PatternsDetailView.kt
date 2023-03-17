package com.zwstudio.lolly.views.patterns

import com.zwstudio.lolly.models.wpp.MPattern
import tornadofx.*

class PatternsDetailView : Fragment("Patterns in Language Detail") {
    val item : MPattern by param()

    override val root = form {
        fieldset {
            field("ID") {
                textfield("${item.id}") {
                    isEditable = false
                }
            }
            field("PATTERN") {
                textfield(item.pattern) {
                    isEditable = false
                }
            }
            field("TITLE") {
                textfield(item.title) {
                    isEditable = false
                }
            }
            field("URL") {
                textfield(item.url) {
                    isEditable = false
                }
            }
        }
        buttonbar {
            button("Close") {
                isDefaultButton = true
            }.action {
                close()
            }
        }
    }
}

