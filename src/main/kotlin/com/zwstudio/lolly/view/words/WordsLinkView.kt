package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsLinkViewModel
import com.zwstudio.lolly.domain.wpp.MLangWord
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableView
import tornadofx.*

class WordsLinkView : Fragment("Link Words") {
    var tvWords: TableView<MLangWord> by singleAssign()
    val vm: WordsLinkViewModel by param()
    var result = false

    override val root = vbox(10.0) {
        hbox(10.0) {
            alignment = Pos.CENTER
            button("Check All").action {
                vm.checkItems(0, tvWords.selectionModel.selectedItems)
            }
            button("Uncheck All").action {
                vm.checkItems(1, tvWords.selectionModel.selectedItems)
            }
            button("Check Selected").action {
                vm.checkItems(2, tvWords.selectionModel.selectedItems)
            }
            button("Uncheck Selected").action {
                vm.checkItems(3, tvWords.selectionModel.selectedItems)
            }
            children.forEach {
                (it as Button).prefWidth = 150.0
            }
        }
        tvWords = tableview(vm.vm.lstWords) {
            column("", MLangWord::isChecked).makeEditable()
            readonlyColumn("ID", MLangWord::id)
            readonlyColumn("WORD", MLangWord::wordProperty)
            readonlyColumn("NOTE", MLangWord::noteProperty)
            readonlyColumn("ACCURACY", MLangWord::accuracy)
            readonlyColumn("FAMIID", MLangWord::famiid)
            selectionModel.selectionMode = SelectionMode.MULTIPLE
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
            }.action {
                result = true
                vm.commit()
                close()
            }
            button("Cancel") {
                isCancelButton = true
            }.action {
                close()
            }
        }
    }
}

