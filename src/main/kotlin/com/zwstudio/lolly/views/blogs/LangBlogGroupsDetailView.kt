package com.zwstudio.lolly.views.blogs

import com.zwstudio.lolly.models.blogs.MLangBlogGroup
import tornadofx.*

class LangBlogGroupsDetailView : Fragment("Patterns in Language Detail") {
    val item : MLangBlogGroup by param()

    override val root = form {
        fieldset {
            field("ID") {
                textfield("${item.id}") {
                    isEditable = false
                }
            }
            field("GROUP") {
                textfield(item.groupname) {
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

