package com.zwstudio.lolly.views.blogs

import com.zwstudio.lolly.models.blogs.MLangBlogGroup
import com.zwstudio.lolly.models.blogs.MLangBlogPost
import com.zwstudio.lolly.viewmodels.blogs.LangBlogGroupsViewModel
import com.zwstudio.lolly.views.ILollySettings
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class LangBlogGroupsView : Fragment("Language Blog Groups"), ILollySettings {
    var tvGroups: TableView<MLangBlogGroup> by singleAssign()
    var tvPosts: TableView<MLangBlogPost> by singleAssign()
    var vm = LangBlogGroupsViewModel()
    var wvWebPage: WebView by singleAssign()

    override val root = vbox {
        toolbar {
            button("Refresh Groups").action {
                vm.reloadGroups()
            }
            textfield(vm.groupFilter) {
                promptText = "Group Filter"
            }
            button("Refresh Posts").action {
                vm.reloadPosts()
            }
            textfield(vm.postFilter) {
                promptText = "Post Filter"
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            vbox {
                splitpane(Orientation.VERTICAL) {
                    vgrow = Priority.ALWAYS
                    setDividerPosition(0, 0.8)
                    tvGroups = tableview(vm.lstLangBlogGroups) {
                        vgrow = Priority.ALWAYS
                        readonlyColumn("ID", MLangBlogGroup::id)
                        column("GROUPNAME", MLangBlogGroup::groupnameProperty).makeEditable()
                        onSelectionChange {
                            vm.selectedGroup.value = it
                            vm.reloadPosts()
                        }
                    }
                    tvPosts = tableview(vm.lstLangBlogPosts) {
                        readonlyColumn("ID", MLangBlogPost::id)
                        column("TITLE", MLangBlogPost::titleProperty).makeEditable()
                        column("URL", MLangBlogPost::urlProperty).makeEditable()
                        onSelectionChange {
                            vm.selectedPost.value = it
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
        vm.reloadGroups()
    }
}
