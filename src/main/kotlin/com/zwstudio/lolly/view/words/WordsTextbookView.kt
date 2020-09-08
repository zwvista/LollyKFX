package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.copyText
import com.zwstudio.lolly.data.googleString
import com.zwstudio.lolly.data.words.WordsUnitDetailViewModel
import com.zwstudio.lolly.data.words.WordsUnitViewModel
import com.zwstudio.lolly.domain.wpp.MUnitWord
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class WordsTextbookView : WordsBaseView("Words in Textbook") {
    var vm = WordsUnitViewModel(false)
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsTextbookView
        toolbarDicts = toolbar()
        toolbar {
            button("Refresh").action {
                vm.reload()
            }
            choicebox(vm.scopeFilter, vmSettings.lstScopeWordFilters)
            textfield(vm.textFilter) {
                promptText = "Filter"
            }
            combobox(vm.textbookFilter, vmSettings.lstTextbookFilters)
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            vbox {
                tableview(vm.lstWords) {
                    vgrow = Priority.ALWAYS
                    readonlyColumn("TEXTBOOKNAME", MUnitWord::textbookname)
                    readonlyColumn("UNIT", MUnitWord::unitstr)
                    readonlyColumn("PART", MUnitWord::partstr)
                    readonlyColumn("SEQNUM", MUnitWord::seqnum)
                    column("WORD", MUnitWord::wordProperty).makeEditable()
                    column("NOTE", MUnitWord::noteProperty).makeEditable()
                    readonlyColumn("ACCURACY", MUnitWord::accuracy)
                    readonlyColumn("WORDID", MUnitWord::wordid)
                    readonlyColumn("ID", MUnitWord::id)
                    readonlyColumn("FAMIID", MUnitWord::famiid)
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
                        val modal = find<WordsTextbookDetailView>("vmDetail" to WordsUnitDetailViewModel(vm, selectionModel.selectedItem)) { openModal(block = true) }
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
