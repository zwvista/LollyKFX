package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesLinkViewModel
import com.zwstudio.lolly.domain.wpp.MLangPhrase
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableView
import tornadofx.*

class PhrasesLinkView : Fragment("Phrases in Unit Detail") {
    var tvPhrases: TableView<MLangPhrase> by singleAssign()
    val vm : PhrasesLinkViewModel by param()
    var result = false

    override val root = vbox(10.0) {
        hbox(10.0) {
            alignment = Pos.CENTER
            button("Check All").action {
                vm.checkItems(0, tvPhrases.selectionModel.selectedItems)
            }
            button("Uncheck All").action {
                vm.checkItems(1, tvPhrases.selectionModel.selectedItems)
            }
            button("Check Selected").action {
                vm.checkItems(2, tvPhrases.selectionModel.selectedItems)
            }
            button("Uncheck Selected").action {
                vm.checkItems(3, tvPhrases.selectionModel.selectedItems)
            }
            children.forEach {
                (it as Button).prefWidth = 150.0
            }
        }
        tvPhrases = tableview(vm.vm.lstPhrases) {
            column("", MLangPhrase::isChecked).makeEditable()
            readonlyColumn("ID", MLangPhrase::id)
            column("PHRASE", MLangPhrase::phraseProperty).makeEditable()
            column("TRANSLATION", MLangPhrase::translationProperty).makeEditable()
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

