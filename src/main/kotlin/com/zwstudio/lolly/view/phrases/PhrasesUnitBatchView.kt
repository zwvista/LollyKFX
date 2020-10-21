package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesUnitBatchViewModel
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableView
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesUnitBatchView : Fragment("Phrases in Unit Batch Edit") {
    var tvPhrases: TableView<MUnitPhrase> by singleAssign()
    val vm : PhrasesUnitBatchViewModel by param()
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
                    enableWhen { vm.unitIsChecked }
                }
            }
            row {
                checkbox("PART:", vm.partIsChecked)
                combobox(vm.partitem, vm.selectedTextbook.lstParts) {
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
            button("Check All") {
                prefWidth = 150.0
            }.action {
                vm.checkItems(0, tvPhrases.selectionModel.selectedItems)
            }
            button("Uncheck All") {
                prefWidth = 150.0
            }.action {
                vm.checkItems(1, tvPhrases.selectionModel.selectedItems)
            }
            button("Check Selected") {
                prefWidth = 150.0
            }.action {
                vm.checkItems(2, tvPhrases.selectionModel.selectedItems)
            }
            button("Uncheck Selected") {
                prefWidth = 150.0
            }.action {
                vm.checkItems(3, tvPhrases.selectionModel.selectedItems)
            }
        }
        tvPhrases = tableview(vm.vm.lstPhrases) {
            column("", MUnitPhrase::isChecked).makeEditable()
            readonlyColumn("UNIT", MUnitPhrase::unitstr)
            readonlyColumn("PART", MUnitPhrase::partstr)
            readonlyColumn("SEQNUM", MUnitPhrase::seqnum)
            readonlyColumn("PHRASE", MUnitPhrase::phrase)
            readonlyColumn("TRANSLATION", MUnitPhrase::translation)
            readonlyColumn("PHRASEID", MUnitPhrase::phraseid)
            readonlyColumn("ID", MUnitPhrase::id)
            selectionModel.selectionMode = SelectionMode.MULTIPLE
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
                action {
                    result = true
                    vm.commit()
                    close()
                }
            }
            button("Cancel") {
                isCancelButton = true
                action {
                    close()
                }
            }
        }
    }
}

