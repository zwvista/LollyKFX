package com.zwstudio.lolly.views.misc

import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import com.zwstudio.lolly.models.misc.MSelectItem
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.layout.Priority
import tornadofx.*

class SettingsView : Fragment("Settings") {
    var cbUnitTo: ComboBox<MSelectItem> by singleAssign()
    var cbPartTo: ComboBox<MSelectItem> by singleAssign()
    var cbPartFrom: ComboBox<MSelectItem> by singleAssign()
    val vm : SettingsViewModel by inject()
    var result = false

    override val root = gridpane {
        paddingAll = 10.0
        hgap = 10.0
        vgap = 10.0
//        style = "-fx-background-color: #FF0000;"
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
            hbox {
                label(vm.unitsInAll)
                alignment = Pos.CENTER
            }
            cbPartFrom = combobox(vm.uspartfromItem, vm.lstParts) {
                maxWidth = Double.MAX_VALUE
                enableWhen { vm.partFromIsEnabled }
            }
        }
        row {
            combobox(vm.toTypeProperty, vm.lstToTypes) {
                maxWidth = Double.MAX_VALUE
                cellFormat {
                    text = it.second
                }
            }
            cbUnitTo = combobox(vm.usunittoItem, vm.lstUnits) {
                maxWidth = Double.MAX_VALUE
                enableWhen { vm.unitToIsEnabled }
            }
            hbox {
                label(vm.unitsInAll)
                alignment = Pos.CENTER
            }
            cbPartTo = combobox(vm.usparttoItem, vm.lstParts) {
                maxWidth = Double.MAX_VALUE
                enableWhen { vm.partToIsEnabled }
            }
        }
        row {  }
        hbox(10.0) {
            gridpaneConstraints {
                columnRowIndex(1, 8)
                columnSpan = 3
            }
            button(vm.previousText) {
                enableWhen { vm.previousIsEnabled }
                prefWidth = 100.0
            }.action {
                vm.previousUnitPart()
            }
            button(vm.nextText) {
                enableWhen { vm.nextIsEnabled }
                prefWidth = 100.0
            }.action {
                vm.nextUnitPart()
            }
        }
        row {
            buttonbar {
                gridpaneConstraints {
                    columnSpan = 4
                }
                button("Apply to All Windows") {
                }.action {
                    close()
                }
                button("Apply to Current Window") {
                }.action {
                    close()
                }
                button("Apply to None") {
                    isCancelButton = true
                }.action {
                    close()
                }
            }
        }
    }
}

