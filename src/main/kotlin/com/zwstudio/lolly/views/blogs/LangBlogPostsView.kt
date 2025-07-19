package com.zwstudio.lolly.views.blogs

import com.zwstudio.lolly.viewmodels.blogs.LangBlogPostsViewModel
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class LangBlogPostsView : Fragment("Language Blog Posts") {
    var vm = LangBlogPostsViewModel()
    var wvWebPage: WebView by singleAssign()

    override val root = vbox {
        toolbar {
            button("Refresh Posts").action {
                vm.reloadPosts()
            }
            textfield(vm.postFilter) {
                promptText = "Post Filter"
            }
            button("Refresh Groups").action {
                vm.reloadGroups()
            }
            textfield(vm.groupFilter) {
                promptText = "Group Filter"
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            vbox {
                splitpane(Orientation.VERTICAL) {
                    vgrow = Priority.ALWAYS
                    setDividerPosition(0, 0.8)
                }
            }
            wvWebPage = webview {

            }
        }
    }
}
