package com.zwstudio.lolly.views.words

import com.zwstudio.lolly.viewmodels.words.WordsUnitBatchViewModel
import com.zwstudio.lolly.models.wpp.MUnitWord
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class WordsUnitBatchView : Fragment("Words in Unit Batch Edit") {
    var tvWords: TableView<MUnitWord> by singleAssign()
    val vm : WordsUnitBatchViewModel by param()
    var result = false

    override val root = vbox(10.0) {
        paddingAll = 10.0
        gridpane {
            hgap = 10.0
            vgap = 10.0
            constraintsForColumn(1).hgrow = Priority.ALWAYS
            row {
                checkbox("UNIT:", vm.unitIsChecked)
                combobox(vm.unititem, vm.selectedTextbook.lstUnits) {
                    maxWidth = Double.MAX_VALUE
                    enableWhen { vm.unitIsChecked }
                }
            }
            row {
                checkbox("PART:", vm.partIsChecked)
                combobox(vm.partitem, vm.selectedTextbook.lstParts) {
                    maxWidth = Double.MAX_VALUE
                    enableWhen { vm.partIsChecked }
                }
            }
            row {
                checkbox("SEQNUM(+):", vm.seqNumIsChecked)
                textfield(vm.seqnum) {
                    enableWhen { vm.seqNumIsChecked }
                }
            }
        }
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
            column("", MUnitWord::isChecked).makeEditable()
            readonlyColumn("UNIT", MUnitWord::unitstr)
            readonlyColumn("PART", MUnitWord::partstr)
            readonlyColumn("SEQNUM", MUnitWord::seqnum)
            readonlyColumn("WORD", MUnitWord::word)
            readonlyColumn("NOTE", MUnitWord::note)
            readonlyColumn("ACCURACY", MUnitWord::accuracy)
            readonlyColumn("WORDID", MUnitWord::wordid)
            readonlyColumn("ID", MUnitWord::id)
            readonlyColumn("FAMIID", MUnitWord::famiid)
            selectionModel.selectionMode = SelectionMode.MULTIPLE
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
            }.action {
                result = true
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

