package com.zwstudio.lolly.ui.patterns

import com.zwstudio.lolly.viewmodels.patterns.PatternsWebPagesDetailViewModel
import tornadofx.*

class PatternsWebPageView : Fragment("Pattern WebPage Detail") {
    val vmDetail : PatternsWebPagesDetailViewModel by param()
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
                    isDisable = (vmDetail.id.value != 0)
                }.action {
                    vmDetail.webpageid.value = 0
                    vmDetail.title.value = ""
                    vmDetail.url.value = ""
                }
                button("Existing") {
                    prefWidth = 80.0
                    isDisable = (vmDetail.id.value != 0)
                }.action {
                    val modal = find<WebPageSelectView> { openModal(block = true) }
                    if (modal.result) {
                        val o = modal.vm.selectedWebPage.value
                        vmDetail.webpageid.value = o.id
                        vmDetail.title.value = o.title
                        vmDetail.url.value = o.url
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
                enableWhen { vmDetail.title.isNotBlank().and(vmDetail.url.isNotBlank()) }
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

