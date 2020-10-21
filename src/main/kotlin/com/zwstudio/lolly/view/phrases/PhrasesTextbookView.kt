package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.copyText
import com.zwstudio.lolly.data.misc.googleString
import com.zwstudio.lolly.data.phrases.PhrasesUnitDetailViewModel
import com.zwstudio.lolly.data.phrases.PhrasesUnitViewModel
import com.zwstudio.lolly.domain.wpp.MLangWord
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesTextbookView : PhrasesBaseView("Phrases in Textbook") {
    var vm = PhrasesUnitViewModel(false)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@PhrasesTextbookView
        toolbarDicts = toolbar()
        toolbar {
            button("Refresh").action {
                vm.reload()
            }
            choicebox(vm.scopeFilter, SettingsViewModel.lstScopePhraseFilters)
            textfield(vm.textFilter) {
                promptText = "Filter"
            }
            combobox(vm.textbookFilter, vmSettings.lstTextbookFilters)
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            vbox {
                splitpane(Orientation.VERTICAL) {
                    vgrow = Priority.ALWAYS
                    setDividerPosition(0, 0.8)
                    tableview(vm.lstPhrases) {
                        vgrow = Priority.ALWAYS
                        readonlyColumn("TEXTBOOKNAME", MUnitPhrase::textbookname)
                        readonlyColumn("UNIT", MUnitPhrase::unitstr)
                        readonlyColumn("PART", MUnitPhrase::partstr)
                        readonlyColumn("SEQNUM", MUnitPhrase::seqnum)
                        column("PHRASE", MUnitPhrase::phraseProperty).makeEditable()
                        column("TRANSLATION", MUnitPhrase::translationProperty).makeEditable()
                        readonlyColumn("PHRASEID", MUnitPhrase::phraseid)
                        readonlyColumn("ID", MUnitPhrase::id)
                        onEditCommit {
                            val title = this.tableColumn.text
                            if (title == "Phrase")
                                rowValue.phrase = vmSettings.autoCorrectInput(rowValue.phrase)
                            vm.update(rowValue).subscribe()
                        }
                        onSelectionChange {
                            vm.getWords(it?.phraseid).subscribe {
                                if (vm.lstWords.isNotEmpty())
                                    Platform.runLater {
                                        tvWords.selectionModel.select(0)
                                    }
                            }
                        }
                        onDoubleClick {
                            // https://github.com/edvin/tornadofx/issues/226
                            val modal = find<PhrasesTextbookDetailView>("vmDetail" to PhrasesUnitDetailViewModel(vm, selectionModel.selectedItem)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        contextmenu {
                            item("Delete")
                            separator()
                            item("Copy") {
                                enableWhen { selectionModel.selectedItemProperty().isNotNull }
                            }.action {
                                copyText(selectedItem?.phrase)
                            }
                            item("Google") {
                                enableWhen { selectionModel.selectedItemProperty().isNotNull }
                            }.action {
                                googleString(selectedItem?.phrase)
                            }
                        }
                    }
                    tvWords = tableview(vm.lstWords) {
                        readonlyColumn("ID", MLangWord::id)
                        column("WORD", MLangWord::wordProperty).makeEditable()
                        column("NOTE", MLangWord::noteProperty).makeEditable()
                        readonlyColumn("ACCURACY", MLangWord::accuracy)
                        readonlyColumn("FAMIID", MLangWord::famiid)
                        onSelectionChange {
                            searchDict(it?.word)
                        }
                        contextmenu {
                            item("Copy") {
                                enableWhen { selectionModel.selectedItemProperty().isNotNull }
                            }.action {
                                copyText(selectedItem?.word)
                            }
                            item("Google") {
                                enableWhen { selectionModel.selectedItemProperty().isNotNull }
                            }.action {
                                googleString(selectedItem?.word)
                            }
                            onEditCommit {
                                val title = this.tableColumn.text
                                if (title == "WORD")
                                    rowValue.word = vmSettings.autoCorrectInput(rowValue.word)
                                vmWordsLang.update(rowValue).subscribe()
                            }
                        }
                    }
                }
                label(vm.statusText)
            }
            dictsPane = tabpane {
            }
        }
    }

    init {
        onSettingsChanged()
    }

    override fun onSettingsChanged() {
        vm.reload()
        super.onSettingsChanged()
    }
}
