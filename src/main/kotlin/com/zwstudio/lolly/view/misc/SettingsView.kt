package com.zwstudio.lolly.view.misc

import com.zwstudio.lolly.data.SettingsViewModel
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import tornadofx.*

class SettingsView : Fragment("Settings") {
    val vm : SettingsViewModel by inject()
    var result = false

    override val root = gridpane {
        padding = Insets(10.0)
        hgap = 10.0
        vgap = 10.0
//        setStyle("-fx-background-color: #FF0000;");
        constraintsForColumn(1).hgrow = Priority.ALWAYS
        constraintsForColumn(2).hgrow = Priority.ALWAYS
        constraintsForColumn(3).hgrow = Priority.ALWAYS
        constraintsForRow(2).vgrow = Priority.ALWAYS
        row {
            label("Language:")
            combobox(vm.selectedLangProperty, vm.lstLanguages) {
                maxWidth = Double.MAX_VALUE
                cellFormat {
                    text = it.langname
                }
                gridpaneConstraints {
                    columnSpan = 3
                }
            }
        }
        row {
            label("Voice:")
            combobox(vm.selectedVoiceProperty, vm.lstVoices) {
                maxWidth = Double.MAX_VALUE
                cellFormat {
                    text = it.voicename
                }
                gridpaneConstraints {
                    columnSpan = 3
                }
            }
        }
        row {
            label("Dictionary(Reference):")
            listview(values = vm.selectedDictsReference) {
                maxWidth = Double.MAX_VALUE
                prefHeight = 100.0
                cellFormat {
                    text = it.dictname
                }
                gridpaneConstraints {
                    columnSpan = 2
                }
            }
        }
        button("Edit") {
            gridpaneConstraints {
                columnRowIndex(3, 2)
            }
        }
        row {
            label("Dictionary(Note):")
            combobox(vm.selectedDictNoteProperty, vm.lstDictsNote) {
                maxWidth = Double.MAX_VALUE
                cellFormat {
                    text = it.dictname
                }
                gridpaneConstraints {
                    columnSpan = 3
                }
            }
        }
        row {
            label("Dictionary(Translation):")
            combobox(vm.selectedDictTranslationProperty, vm.lstDictsTranslation) {
                maxWidth = Double.MAX_VALUE
                cellFormat {
                    text = it.dictname
                }
                gridpaneConstraints {
                    columnSpan = 3
                }
            }
        }
        row {
            label("Textbooks:")
            combobox(vm.selectedTextbookProperty, vm.lstTextbooks) {
                maxWidth = Double.MAX_VALUE
                cellFormat {
                    text = it.textbookname
                }
                gridpaneConstraints {
                    columnSpan = 3
                }
            }
        }
        row {
            label("Unit:")
            combobox(vm.usunitfromItem, vm.lstUnits) {
                maxWidth = Double.MAX_VALUE
            }
            label(vm.unitsInAll)
            combobox(vm.uspartfromItem, vm.lstParts) {
                maxWidth = Double.MAX_VALUE
            }
        }
        row {
            combobox(vm.toTypeProperty, vm.lstToTypes) {
                maxWidth = Double.MAX_VALUE
            }
            combobox(vm.usunittoItem, vm.lstUnits) {
                maxWidth = Double.MAX_VALUE
            }
            label(vm.unitsInAll)
            combobox(vm.usparttoItem, vm.lstParts) {
                maxWidth = Double.MAX_VALUE
            }
        }
        row {  }
        buttonbar {
            alignment = Pos.BASELINE_LEFT
            gridpaneConstraints {
                columnRowIndex(1, 8)
                columnSpan = 3
            }
            button("Previous")
            button("Next")
        }
        row {
            buttonbar {
                gridpaneConstraints {
                    columnSpan = 4
                }
                button("Apply to All Windows") {
                    action {
                        close()
                    }
                }
                button("Apply to Current Window") {
                    action {
                        close()
                    }
                }
                button("Apply to None") {
                    isCancelButton = true
                    action {
                        close()
                    }
                }
            }
        }
    }
}

