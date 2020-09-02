package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.phrases.PhrasesUnitBatchViewModel
import com.zwstudio.lolly.domain.wpp.MUnitPhrase
import javafx.geometry.Insets
import javafx.scene.layout.Priority
import tornadofx.*

class PhrasesUnitBatchView : Fragment("Phrases in Unit Batch Edit") {
    val vm : PhrasesUnitBatchViewModel by param()
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
        buttonbar {
            button("Check All")
            button("Uncheck All")
            button("Check Selected")
            button("Uncheck Selected")
        }
        tableview(vm.vm.lstPhrases) {
            readonlyColumn("UNIT", MUnitPhrase::unitstr)
            readonlyColumn("PART", MUnitPhrase::partstr)
            readonlyColumn("SEQNUM", MUnitPhrase::seqnum)
            readonlyColumn("PHRASE", MUnitPhrase::phrase)
            readonlyColumn("TRANSLATION", MUnitPhrase::translation)
            readonlyColumn("PHRASEID", MUnitPhrase::phraseid)
            readonlyColumn("ID", MUnitPhrase::id)
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

