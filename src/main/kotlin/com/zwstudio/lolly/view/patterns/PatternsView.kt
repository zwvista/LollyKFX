package com.zwstudio.lolly.view.patterns

import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.patterns.PatternsDetailViewModel
import com.zwstudio.lolly.data.patterns.PatternsViewModel
import com.zwstudio.lolly.data.patterns.PatternsWebPageViewModel
import com.zwstudio.lolly.domain.wpp.MPattern
import com.zwstudio.lolly.domain.wpp.MPatternPhrase
import com.zwstudio.lolly.domain.wpp.MPatternWebPage
import com.zwstudio.lolly.view.ILollySettings
import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import tornadofx.*

class PatternsView : Fragment("Patterns in Language"), ILollySettings {
    var vm = PatternsViewModel()
    var btnAddWebPage: Button by singleAssign()
    var tvPatterns: TableView<MPattern> by singleAssign()
    var tvWebPages: TableView<MPatternWebPage> by singleAssign()
    var wvWebPage: WebView by singleAssign()

    override val root = vbox {
        tag = this@PatternsView
        toolbar {
            button("Add Pattern").action {
                val modal = find<PatternsDetailView>("vmDetail" to PatternsDetailViewModel(vm, vm.newPattern())) { openModal(block = true) }
                if (modal.result)
                    tvPatterns.refresh()
            }
            btnAddWebPage = button("Add Web Page") {
                isDisable = true
                action {
                    val o = tvPatterns.selectedItem!!
                    val modal = find<PatternsWebPagelView>("vmDetail" to PatternsWebPageViewModel(vm, vm.newPatternWebPage(o.id, o.pattern))) { openModal(block = true) }
                    if (modal.result)
                        tvWebPages.refresh()
                }
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
                        readonlyColumn("ID", MPattern::id)
                        column("PATTERN", MPattern::pattern)
                        column("NOTE", MPattern::noteProperty)
                        column("TAGS", MPattern::tagsProperty)
                        onSelectionChange {
                            it?.id?.let {
                                btnAddWebPage.isDisable = false
                                vm.getWebPages(it).subscribe {
                                    if (it.isNotEmpty())
                                    // https://stackoverflow.com/questions/20413419/javafx-2-how-to-focus-a-table-row-programmatically
                                        Platform.runLater {
                                            //                                        tvWebPage.requestFocus()
                                            tvWebPages.selectionModel.select(0)
                                        }
                                }
                                vm.getPhrases(it).subscribe()
                            }
                        }
                        onDoubleClick {
                            val modal = find<PatternsDetailView>("vmDetail" to PatternsDetailViewModel(vm, selectionModel.selectedItem)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
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
                            val modal = find<PatternsWebPagelView>("vmDetail" to PatternsWebPageViewModel(vm, selectionModel.selectedItem)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                    }
                }
                label(vm.statusText)
            }
            splitpane(Orientation.VERTICAL) {
                setDividerPosition(0, 0.2)
                tableview(vm.lstPhrases) {
                    vgrow = Priority.ALWAYS
                    readonlyColumn("ID", MPatternPhrase::id)
                    column("PHRASE", MPatternPhrase::phrase)
                    column("TRANSLATION", MPatternPhrase::translation)
                }
                wvWebPage = webview {

                }
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
