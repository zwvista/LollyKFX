package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsLangViewModel
import com.zwstudio.lolly.domain.wpp.LangWordViewModel
import com.zwstudio.lolly.domain.wpp.MLangWord
import javafx.geometry.Orientation
import javafx.scene.layout.Priority
import tornadofx.*

class WordsLangView : WordsBaseView("Words in Language") {
    var vm = WordsLangViewModel()
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsLangView
        toolbarDicts = toolbar()
        toolbar {
            button("Add")
            button("Refresh").action {
                vm.reload()
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tableview(vm.lstWords) {
                readonlyColumn("ID", MLangWord::id)
                column("WORD", MLangWord::wordProperty).makeEditable()
                column("NOTE", MLangWord::noteProperty).makeEditable()
                readonlyColumn("LEVEL", MLangWord::level)
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
                    val modal = find<WordsLangDetailView>("model" to LangWordViewModel(selectionModel.selectedItem)) { openModal(block = true) }
                    if (modal.result)
                        this.refresh()
                }
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
