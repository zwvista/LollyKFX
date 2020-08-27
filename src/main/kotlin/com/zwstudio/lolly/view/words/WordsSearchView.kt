package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsSearchViewModel
import com.zwstudio.lolly.domain.wpp.MUnitWord
import javafx.geometry.Orientation
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class WordsSearchView : WordsBaseView("Search") {
    var tvWords: TableView<MUnitWord> by singleAssign()
    var vm = WordsSearchViewModel()
    override val vmSettings get() = vm.vmSettings

    override val root = vbox {
        tag = this@WordsSearchView
        toolbarDicts = toolbar()
        toolbar {
            button("Refresh").action {
                vm.reload()
            }
            textfield(vm.newWord) {
                promptText = "New Word"
                action {
                    val item = MUnitWord().apply {
                        seqnum = vm.lstWords.size + 1
                        word = vm.newWord.value
                    }
                    vm.lstWords.add(item)
                    vm.newWord.value = ""
                    tvWords.refresh()
                    tvWords.selectionModel.select(vm.lstWords.size - 1)
                }
            }
        }
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            tvWords = tableview(vm.lstWords) {
                readonlyColumn("SEQNUM", MUnitWord::seqnum)
                column("WORD", MUnitWord::wordProperty).makeEditable()
                column("NOTE", MUnitWord::noteProperty).makeEditable()
                onEditCommit {
                    val title = this.tableColumn.text
                    if (title == "WORD")
                        rowValue.word = vmSettings.autoCorrectInput(rowValue.word)
                }
                onSelectionChange {
                    onWordChanged(it?.word)
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
