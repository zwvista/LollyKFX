package com.zwstudio.lolly.view.patterns

import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.copyText
import com.zwstudio.lolly.data.misc.googleString
import com.zwstudio.lolly.data.patterns.*
import com.zwstudio.lolly.domain.wpp.MPattern
import com.zwstudio.lolly.domain.wpp.MPatternWebPage
import com.zwstudio.lolly.view.ILollySettings
import javafx.application.Platform
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
            button("Add Pattern").action {
                val modal = find<PatternsDetailView>("vmDetail" to PatternsDetailViewModel(vm, vm.newPattern())) { openModal(block = true) }
                if (modal.result)
                    tvPatterns.refresh()
            }
            button("Add Web Page") {
                isDisable = true
                btnAddWebPage = this
            }.action {
                val o = tvPatterns.selectedItem!!
                val modal = find<PatternsWebPageView>("vmDetail" to PatternsWebPageViewModel(vm, vm.newPatternWebPage(o.id, o.pattern))) { openModal(block = true) }
                if (modal.result)
                    tvWebPages.refresh()
            }
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
                splitpane(Orientation.VERTICAL) {
                    vgrow = Priority.ALWAYS
                    setDividerPosition(0, 0.8)
                    tvPatterns = tableview(vm.lstPatterns) {
                        vgrow = Priority.ALWAYS
                        selectionModel.selectionMode = SelectionMode.MULTIPLE
                        readonlyColumn("ID", MPattern::id)
                        column("PATTERN", MPattern::pattern)
                        column("NOTE", MPattern::noteProperty)
                        column("TAGS", MPattern::tagsProperty)
                        onSelectionChange {
                            btnAddWebPage.isDisable = it == null
                            it?.id?.let {
                                vm.getWebPages(it).subscribe {
                                    if (it.isNotEmpty())
                                    // https://stackoverflow.com/questions/20413419/javafx-2-how-to-focus-a-table-row-programmatically
                                        Platform.runLater {
                                            // tvWebPage.requestFocus()
                                            tvWebPages.selectionModel.select(0)
                                        }
                                }
                            }
                        }
                        fun edit() {
                            val modal = find<PatternsDetailView>("vmDetail" to PatternsDetailViewModel(vm, selectedItem!!)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        onDoubleClick {
                        }
                        contextmenu {
                            item("Edit").action {
                                edit()
                            }
                            separator()
                            item("Delete").action {

                            }
                            separator()
                            item("Merge").action {
                                val modal = find<PatternsMergeView>("vmMerge" to PatternsMergeViewModel(selectionModel.selectedItems)) { openModal(block = true) }
                                if (modal.result)
                                    this@tableview.refresh()
                            }
                            item("Split").action {
                                val modal = find<PatternsSplitView>("vmSplit" to PatternsSplitViewModel(selectedItem!!)) { openModal(block = true) }
                                if (modal.result)
                                    this@tableview.refresh()
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
                    tvWebPages = tableview(vm.lstWebPages) {
                        readonlyColumn("ID", MPatternWebPage::id)
                        readonlyColumn("SEQNUM", MPatternWebPage::seqnum)
                        column("TITLE", MPatternWebPage::title)
                        column("URL", MPatternWebPage::url)
                        column("WEBPAGEID", MPatternWebPage::webpageid)
                        onSelectionChange {
                            it?.url?.let {
                                wvWebPage.engine.load(it)
                            }
                        }
                        onDoubleClick {
                            val modal = find<PatternsWebPageView>("vmDetail" to PatternsWebPageViewModel(vm, selectedItem!!)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
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
