package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.misc.SettingsViewModel
import com.zwstudio.lolly.data.misc.copyText
import com.zwstudio.lolly.data.misc.googleString
import com.zwstudio.lolly.data.phrases.PhrasesLangDetailViewModel
import com.zwstudio.lolly.data.words.WordsLangDetailViewModel
import com.zwstudio.lolly.data.words.WordsLangViewModel
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import com.zwstudio.lolly.domain.wpp.MLangWord
import com.zwstudio.lolly.view.phrases.PhrasesLangDetailView
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
                            vm.update(rowValue).subscribe()
                        }
                        onSelectionChange {
                            searchDict(it?.word)
                            vmPhrasesLang.getPhrases(it?.id).subscribe()
                        }
                        fun edit() {
                            // https://github.com/edvin/tornadofx/issues/226
                            val modal = find<WordsLangDetailView>("vmDetail" to WordsLangDetailViewModel(vm, selectionModel.selectedItem)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        onDoubleClick {
                            edit()
                        }
                        contextmenu {
                            item("Edit").action {
                                edit()
                            }
                            item("Link Existing Phrases").action {
                                edit()
                            }
                            separator()
                            item("Delete")
                            separator()
                            item("Copy").action {
                                copyText(selectedItem?.word)
                            }
                            item("Google").action {
                                googleString(selectedItem?.word)
                            }
                            items.forEach {
                                it.enableWhen { selectionModel.selectedItemProperty().isNotNull }
                            }
                        }
                    }
                    tableview(vmPhrasesLang.lstPhrases) {
                        readonlyColumn("ID", MLangPhrase::id)
                        column("PHRASE", MLangPhrase::phraseProperty).makeEditable()
                        column("TRANSLATION", MLangPhrase::translationProperty).makeEditable()
                        onEditCommit {
                            val title = this.tableColumn.text
                            if (title == "Phrase")
                                rowValue.phrase = vmSettings.autoCorrectInput(rowValue.phrase)
                            vmPhrasesLang.update(rowValue).subscribe()
                        }
                        fun edit() {
                            // https://github.com/edvin/tornadofx/issues/226
                            val modal = find<PhrasesLangDetailView>("vmDetail" to PhrasesLangDetailViewModel(vmPhrasesLang, selectionModel.selectedItem)) { openModal(block = true) }
                            if (modal.result)
                                this.refresh()
                        }
                        onDoubleClick {
                            edit()
                        }
                        contextmenu {
                            item("Edit").action {
                                edit()
                            }
                            separator()
                            item("Unlink").action {

                            }
                            separator()
                            item("Copy").action {
                                copyText(selectedItem?.phrase)
                            }
                            item("Google").action {
                                googleString(selectedItem?.phrase)
                            }
                            items.forEach {
                                it.enableWhen { selectionModel.selectedItemProperty().isNotNull }
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
