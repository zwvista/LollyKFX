package com.zwstudio.lolly.view.patterns

import com.zwstudio.lolly.data.patterns.PatternsWebPageViewModel
import tornadofx.*

class PatternsWebPagelView : Fragment("Pattern WebPage Detail") {
    val vmDetail : PatternsWebPageViewModel by param()
    var result = false

    override val root = form {
        fieldset {
            field("ID") {
                textfield(vmDetail.id) {
                    isEditable = false
                }
            }
            field("PATTERNID") {
                textfield(vmDetail.patternid) {
                    isEditable = false
                }
            }
            field("PATTERN") {
                textfield(vmDetail.pattern) {
                    isEditable = false
                }
            }
            field("SEQNUM") {
                textfield(vmDetail.seqnum)
            }
            field("WEBPAGEID") {
                textfield(vmDetail.webpageid) {
                    isEditable = false
                }
                button("New") {
                    prefWidth = 80.0
                    action {
                        vmDetail.webpageid.value = 0
                    }
                }
                button("Existing") {
                    prefWidth = 80.0
                    action {

                    }
                }
            }
            field("TITLE") {
                textfield(vmDetail.title)
            }
            field("URL") {
                textfield(vmDetail.url)
            }
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
                action {
                    result = true
                    vmDetail.commit()
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

