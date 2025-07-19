package com.zwstudio.lolly.views.blogs

import com.zwstudio.lolly.models.blogs.MLangBlogPost
import tornadofx.*

class LangBlogPostsDetailView : Fragment("Patterns in Language Detail") {
    val item : MLangBlogPost by param()

    override val root = form {
        fieldset {
            field("ID") {
                textfield("${item.id}") {
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

