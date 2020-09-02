package com.zwstudio.lolly.view.misc

import com.zwstudio.lolly.data.SettingsViewModel
import com.zwstudio.lolly.data.UnitPartToType
import com.zwstudio.lolly.data.applyIO
import com.zwstudio.lolly.domain.MSelectItem
import javafx.geometry.Insets
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
                setOnAction {
                    vm.setSelectedLang(vm.selectedLang).applyIO().subscribe()
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
                setOnAction {
                    vm.updateVoice().subscribe()
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
                setOnAction {
                    vm.updateDictNote().subscribe()
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
                setOnAction {
                    vm.updateDictReference().subscribe()
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
                setOnAction {
                    vm.updateTextbook().subscribe()
                }
            }
        }
        row {
            label("Unit:")
            combobox(vm.usunitfromItem, vm.lstUnits) {
                maxWidth = Double.MAX_VALUE
                setOnAction {
                    vm.updateUnitFrom()
                }
            }
            label(vm.unitsInAll)
            cbPartFrom = combobox(vm.uspartfromItem, vm.lstParts) {
                maxWidth = Double.MAX_VALUE
                setOnAction {
                    vm.updatePartFrom()
                }
            }
        }
        row {
            combobox(vm.toTypeProperty, vm.lstToTypes) {
                maxWidth = Double.MAX_VALUE
                setOnAction {
                    val b = vm.toType == UnitPartToType.To
                    cbUnitTo.isDisable = !b
                    cbPartTo.isDisable = !b || vm.isSinglePart
                    btnPrevious.isDisable = b
                    btnNext.isDisable = b
                    val b2 = vm.toType != UnitPartToType.Unit
                    val t = if (!b2) "Unit" else "Part"
                    btnPrevious.text = "Previous " + t
                    btnNext.text = "Next " + t
                    cbPartFrom.isDisable = !b2 || vm.isSinglePart
                    vm.updateToType()
                }
            }
            cbUnitTo = combobox(vm.usunittoItem, vm.lstUnits) {
                maxWidth = Double.MAX_VALUE
                setOnAction {
                    vm.updateUnitTo()
                }
            }
            label(vm.unitsInAll)
            cbPartTo = combobox(vm.usparttoItem, vm.lstParts) {
                maxWidth = Double.MAX_VALUE
                setOnAction {
                    vm.updatePartTo()
                }
            }
        }
        row {  }
        hbox {
            gridpaneConstraints {
                columnRowIndex(1, 8)
                columnSpan = 3
            }
            children.style {

            }
            btnPrevious = button("Previous")
            btnNext = button("Next")
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

