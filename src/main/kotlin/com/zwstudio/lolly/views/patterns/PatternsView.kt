package com.zwstudio.lolly.views.patterns

import com.zwstudio.lolly.models.wpp.MPattern
import com.zwstudio.lolly.models.wpp.MPatternWebPage
import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.viewmodels.misc.copyText
import com.zwstudio.lolly.viewmodels.misc.googleString
import com.zwstudio.lolly.viewmodels.patterns.PatternsViewModel
import com.zwstudio.lolly.views.ILollySettings
import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class PatternsView : Fragment("Patterns in Language"), ILollySettings {
    var vm = PatternsViewModel()
    var tvPatterns: TableView<MPattern> by singleAssign()
    var tvWebPages: TableView<MPatternWebPage> by singleAssign()
    var wvWebPage: WebView by singleAssign()
    var btnAddWebPage: Button by singleAssign()

    override val root = vbox {
        tag = this@PatternsView
        toolbar {
            button("Refresh").action {
                vm.reload()
            }
            choicebox(vm.scopeFilter, SettingsViewModel.lstScopePatternFilters)
            textfield(vm.textFilter) {
                promptText = "Filter"
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            vbox {
                tvPatterns = tableview(vm.lstPatterns) {
                    vgrow = Priority.ALWAYS
                    selectionModel.selectionMode = SelectionMode.MULTIPLE
                    readonlyColumn("ID", MPattern::id)
                    readonlyColumn("PATTERN", MPattern::pattern)
                    readonlyColumn("TAGS", MPattern::tags)
                    readonlyColumn("TITLE", MPattern::title)
                    readonlyColumn("URL", MPattern::url)
                    onSelectionChange {
                        it?.url?.let {
                            wvWebPage.engine.load(it)
                        }
                    }
                    fun edit() {
                        find<PatternsDetailView>("item" to selectedItem!!) { openModal(block = true) }
                    }
                    onDoubleClick {
                        edit()
                    }
                    contextmenu {
                        item("Edit").action {
                            edit()
                        }
                        separator()
                        item("Copy").action {
                            copyText(selectedItem?.pattern)
                        }
                        item("Google").action {
                            googleString(selectedItem?.pattern)
                        }
                        items.forEach {
                            it.enableWhen { selectionModel.selectedItemProperty().isNotNull }
                        }
                    }
                }
                label(vm.statusText)
            }
            wvWebPage = webview {

            }
        }
    }

    init {
        onSettingsChanged()
    }

    override fun onSettingsChanged() {
        vm.reload()
    }
}
