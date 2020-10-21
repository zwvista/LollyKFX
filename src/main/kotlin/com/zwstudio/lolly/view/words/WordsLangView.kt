package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.copyText
import com.zwstudio.lolly.data.misc.googleString
import com.zwstudio.lolly.data.words.WordsLangDetailViewModel
import com.zwstudio.lolly.data.words.WordsLangViewModel
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import com.zwstudio.lolly.domain.wpp.MLangWord
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class WordsLangView : WordsBaseView("Words in Language") {
    var tvWords: TableView<MLangWord> by singleAssign()
    var vm = WordsLangViewModel()
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsLangView
        toolbarDicts = toolbar()
        toolbar {
            button("Add").action {
                val modal = find<WordsLangDetailView>("vmDetail" to WordsLangDetailViewModel(vm, vm.newLangWord())) { openModal(block = true) }
                if (modal.result) {
                    vm.lstWords.add(modal.vmDetail.item)
                    tvWords.refresh()
                }
            }
            button("Refresh").action {
                vm.reload()
            }
            textfield(vm.newWord) {
                promptText = "New Word"
            }.action {
                val item = vm.newLangWord().apply { word = vm.newWord.value }
                vm.lstWords.add(item)
                tvWords.refresh()
            }
            choicebox(vm.scopeFilter, SettingsViewModel.lstScopeWordFilters)
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
                    tvWords = tableview(vm.lstWords) {
                        vgrow = Priority.ALWAYS
                        readonlyColumn("ID", MLangWord::id)
                        column("WORD", MLangWord::wordProperty).makeEditable()
                        column("NOTE", MLangWord::noteProperty).makeEditable()
                        readonlyColumn("ACCURACY", MLangWord::accuracy)
                        readonlyColumn("FAMIID", MLangWord::famiid)
                        onEditCommit {
                            val title = this.tableColumn.text
                            if (title == "WORD")
                                rowValue.word = vmSettings.autoCorrectInput(rowValue.word)
                        }
                        onSelectionChange {
                            searchDict(it?.word)
                            vm.getPhrases(it?.id).subscribe()
                        }
                        onDoubleClick {
                            // https://github.com/edvin/tornadofx/issues/226
                            val modal = find<WordsLangDetailView>("vmDetail" to WordsLangDetailViewModel(vm, selectionModel.selectedItem)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        contextmenu {
                            item("Delete") {
                                enableWhen { selectionModel.selectedItemProperty().isNotNull }
                            }
                            separator()
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
                        }
                    }
                    tableview(vm.lstPhrases) {
                        readonlyColumn("ID", MLangPhrase::id)
                        column("PHRASE", MLangPhrase::phraseProperty)
                        column("TRANSLATION", MLangPhrase::translationProperty)
                        contextmenu {
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
                }
                label(vm.statusText)
            }
            dictsPane = tabpane {
                vgrow = Priority.ALWAYS
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
