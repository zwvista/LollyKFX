package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.copyText
import com.zwstudio.lolly.data.misc.googleString
import com.zwstudio.lolly.data.phrases.PhrasesLangDetailViewModel
import com.zwstudio.lolly.data.phrases.PhrasesLangViewModel
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import com.zwstudio.lolly.domain.wpp.MLangWord
import javafx.application.Platform
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesLangView : PhrasesBaseView("Phrases in Language") {
    var tvPhrases: TableView<MLangPhrase> by singleAssign()
    var vm = PhrasesLangViewModel()
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@PhrasesLangView
        toolbarDicts = toolbar()
        toolbar {
            button("Add").action {
                val modal = find<PhrasesLangDetailView>("vmDetail" to PhrasesLangDetailViewModel(vm, vm.newLangPhrase())) { openModal(block = true) }
                if (modal.result) {
                    vm.lstPhrases.add(modal.vmDetail.item)
                    tvPhrases.refresh()
                }
            }
            button("Refresh").action {
                vm.reload()
            }
            choicebox(vm.scopeFilter, SettingsViewModel.lstScopePhraseFilters)
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
                    tvPhrases = tableview(vm.lstPhrases) {
                        vgrow = Priority.ALWAYS
                        readonlyColumn("ID", MLangPhrase::id)
                        column("PHRASE", MLangPhrase::phraseProperty).makeEditable()
                        column("TRANSLATION", MLangPhrase::translationProperty).makeEditable()
                        onEditCommit {
                            val title = this.tableColumn.text
                            if (title == "Phrase")
                                rowValue.phrase = vmSettings.autoCorrectInput(rowValue.phrase)
                        }
                        onSelectionChange {
                            vm.getWords(it?.id).subscribe {
                                if (vm.lstWords.isNotEmpty())
                                    Platform.runLater {
                                        tvWords.selectionModel.select(0)
                                    }
                            }
                        }
                        onDoubleClick {
                            // https://github.com/edvin/tornadofx/issues/226
                            val modal = find<PhrasesLangDetailView>("vmDetail" to PhrasesLangDetailViewModel(vm, selectionModel.selectedItem)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        contextmenu {
                            item("Delete")
                            separator()
                            item("Copy").action {
                                copyText(selectedItem?.phrase)
                            }
                            item("Google").action {
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
