package com.zwstudio.lolly.view.misc

import com.zwstudio.lolly.data.SettingsViewModel
import com.zwstudio.lolly.domain.MSelectItem
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.layout.Priority
import tornadofx.*

class SettingsView : Fragment("Settings") {
    var cbUnitTo: ComboBox<MSelectItem> by singleAssign()
    var cbPartTo: ComboBox<MSelectItem> by singleAssign()
    var btnPrevious: Button by singleAssign()
    var btnNext: Button by singleAssign()
    var cbPartFrom: ComboBox<MSelectItem> by singleAssign()
    val vm : SettingsViewModel by inject()
    var result = false

    override val root = gridpane {
        paddingAll = 10.0
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
                setOnAction {
                    vm.updateUnitFrom().subscribe()
                }
            }
            hbox {
                label(vm.unitsInAll)
                alignment = Pos.CENTER
            }
            cbPartFrom = combobox(vm.uspartfromItem, vm.lstParts) {
                maxWidth = Double.MAX_VALUE
                enableWhen { vm.partFromIsEnabled }
                setOnAction {
                    vm.updatePartFrom().subscribe()
                }
            }
        }
        row {
            combobox(vm.toTypeProperty, vm.lstToTypes) {
                maxWidth = Double.MAX_VALUE
            }
            cbUnitTo = combobox(vm.usunittoItem, vm.lstUnits) {
                maxWidth = Double.MAX_VALUE
                enableWhen { vm.unitToIsEnabled }
                setOnAction {
                    vm.updateUnitTo().subscribe()
                }
            }
            hbox {
                label(vm.unitsInAll)
                alignment = Pos.CENTER
            }
            cbPartTo = combobox(vm.usparttoItem, vm.lstParts) {
                maxWidth = Double.MAX_VALUE
                enableWhen { vm.partToIsEnabled }
                setOnAction {
                    vm.updatePartTo().subscribe()
                }
            }
        }
        row {  }
        hbox(10.0) {
            gridpaneConstraints {
                columnRowIndex(1, 8)
                columnSpan = 3
            }
            btnPrevious = button(vm.previousText) {
                enableWhen { vm.previousIsEnabled }
                prefWidth = 100.0
                action {
                    vm.previousUnitPart()
                }
            }
            btnNext = button(vm.nextText) {
                enableWhen { vm.nextIsEnabled }
                prefWidth = 100.0
                action {
                    vm.nextUnitPart()
                }
            }
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

