package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsUnitBatchViewModel
import com.zwstudio.lolly.domain.wpp.MUnitWord
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import tornadofx.*

class WordsUnitBatchView : Fragment("Words in Unit Batch Edit") {
    val vm : WordsUnitBatchViewModel by param()
    var result = false

    override val root = vbox {
        padding = Insets(10.0)
        spacing = 10.0
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
            row {
                checkbox("LEVEL:", vm.levelIsChecked)
                textfield(vm.level) {
                    enableWhen { vm.levelIsChecked }
                }
            }
            row { }
            checkbox("Level = 0 Only", vm.level0OnlyIsChecked) {
                gridpaneConstraints {
                    columnRowIndex(1, 4)
                }
            }
        }
        hbox {
            alignment = Pos.CENTER
            button("Check All")
            button("Uncheck All")
            button("Check Selected")
            button("Uncheck Selected")
        }
        tableview(vm.vm.lstWords) {
            readonlyColumn("UNIT", MUnitWord::unitstr)
            readonlyColumn("PART", MUnitWord::partstr)
            readonlyColumn("SEQNUM", MUnitWord::seqnum)
            readonlyColumn("WORD", MUnitWord::word)
            readonlyColumn("NOTE", MUnitWord::note)
            readonlyColumn("LEVEL", MUnitWord::level)
            readonlyColumn("ACCURACY", MUnitWord::accuracy)
            readonlyColumn("WORDID", MUnitWord::wordid)
            readonlyColumn("ID", MUnitWord::id)
            readonlyColumn("FAMIID", MUnitWord::famiid)
        }
        buttonbar {
            button("OK") {
                isDefaultButton = true
                action {
                    result = true
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

