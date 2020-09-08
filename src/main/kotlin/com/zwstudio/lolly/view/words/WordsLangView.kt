package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.copyText
import com.zwstudio.lolly.data.googleString
import com.zwstudio.lolly.data.words.WordsLangDetailViewModel
import com.zwstudio.lolly.data.words.WordsLangViewModel
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
                action {
                    val item = vm.newLangWord().apply { word = vm.newWord.value }
                    vm.lstWords.add(item)
                    tvWords.refresh()
                }
            }
            choicebox(vm.scopeFilter, vmSettings.lstScopeWordFilters)
            textfield(vm.textFilter) {
                promptText = "Filter"
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            vbox {
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
                        onWordChanged(it?.word)
                    }
                    onDoubleClick {
                        // https://github.com/edvin/tornadofx/issues/226
                        val modal = find<WordsLangDetailView>("vmDetail" to WordsLangDetailViewModel(vm, selectionModel.selectedItem)) { openModal(block = true) }
                        if (modal.result)
                            this.refresh()
                    }
                    contextmenu {
                        item("Delete")
                        separator()
                        item("Copy").action {
                            copyText(selectedItem?.word)
                        }
                        item("Google").action {
                            googleString(selectedItem?.word)
                        }
                    }
                }
                label(vm.statusText)
            }
            splitpane(Orientation.VERTICAL) {
                dictsPane = tabpane {
                    vgrow = Priority.ALWAYS
                }
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
