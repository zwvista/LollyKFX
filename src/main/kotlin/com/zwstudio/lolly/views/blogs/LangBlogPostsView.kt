package com.zwstudio.lolly.views.blogs

import com.zwstudio.lolly.models.blogs.MLangBlogGroup
import com.zwstudio.lolly.models.blogs.MLangBlogPost
import com.zwstudio.lolly.viewmodels.blogs.LangBlogPostsViewModel
import com.zwstudio.lolly.views.ILollySettings
import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class LangBlogPostsView : Fragment("Language Blog Posts"), ILollySettings {
    var tvPosts: TableView<MLangBlogPost> by singleAssign()
    var tvGroups: TableView<MLangBlogGroup> by singleAssign()
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
                    setDividerPosition(0, 0.5)
                    tvPosts = tableview(vm.lstLangBlogPosts) {
                        vgrow = Priority.ALWAYS
                        readonlyColumn("ID", MLangBlogPost::id)
                        column("TITLE", MLangBlogPost::titleProperty).makeEditable()
                        column("URL", MLangBlogPost::urlProperty).makeEditable()
                        onSelectionChange {
                            vm.selectedPost.value = it
                            vm.reloadGroups()
                        }
                        fun editPost() {
                            find<LangBlogPostsDetailView>("item" to selectedItem!!) { openModal(block = true) }
                        }
                        onDoubleClick {
                            editPost()
                        }
                        contextmenu {
                            item("Add Post").action {
                                editPost()
                            }
                            item("Edit Post").action {
                                editPost()
                            }
                            item("Edit Content").action {
                            }
                            items.forEach {
                                it.enableWhen { selectionModel.selectedItemProperty().isNotNull }
                            }
                        }
                    }
                    tvGroups = tableview(vm.lstLangBlogGroups) {
                        readonlyColumn("ID", MLangBlogGroup::id)
                        column("GROUPNAME", MLangBlogGroup::groupnameProperty).makeEditable()
                        fun editGroup() {
                            find<LangBlogGroupsDetailView>("item" to selectedItem!!) { openModal(block = true) }
                        }
                        onDoubleClick {
                            editGroup()
                        }
                        contextmenu {
                            item("Edit Group").action {
                                editGroup()
                            }
                            items.forEach {
                                it.enableWhen { selectionModel.selectedItemProperty().isNotNull }
                            }
                        }
                    }
                }
            }
            wvWebPage = webview {

            }
        }
    }

    init {
        onSettingsChanged()
    }

    override fun onSettingsChanged() {
        vm.reloadPosts()
        vm.postHtml.onChange {
            Platform.runLater { wvWebPage.engine.loadContent(it) }
        }
    }
}
