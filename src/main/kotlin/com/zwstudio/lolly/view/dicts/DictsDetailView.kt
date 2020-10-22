package com.zwstudio.lolly.view.dicts

import com.zwstudio.lolly.data.dicts.DictsDetailViewModel
import com.zwstudio.lolly.data.dicts.TransformEditViewModel
import javafx.geometry.Orientation
import javafx.scene.control.ButtonBar
import javafx.scene.layout.Priority
import tornadofx.*

class DictsDetailView : Fragment("Dictionaries Detail") {
    val vmDetail : DictsDetailViewModel by param()
    var result = false

    override val root = vbox(10.0) {
        paddingAll = 10.0
        splitpane(Orientation.HORIZONTAL) {
            vgrow = Priority.ALWAYS
            gridpane {
                hgap = 10.0
                vgap = 10.0
                constraintsForColumn(1).hgrow = Priority.ALWAYS
                constraintsForRow(11).vgrow = Priority.ALWAYS
                row {
                    label("ID:")
                    textfield(vmDetail.id) {
                        isEditable = false
                    }
                }
                row {
                    label("LANGNAMEFROM:")
                    textfield(vmDetail.langnamefrom) {
                        isEditable = false
                    }
                }
                row {
                    label("LANGNAMETO:")
                    combobox(vmDetail.langtoitem, vmDetail.vm.vmSettings.lstLanguagesAll) {
                        maxWidth = Double.MAX_VALUE
                        cellFormat {
                            text = it.langname
                        }
                    }
                }
                row {
                    label("SEQNUM:")
                    textfield(vmDetail.seqnum)
                }
                row {
                    label("DICTTYPE:")
                    combobox(vmDetail.dicttypeitem, vmDetail.vm.vmSettings.lstDictTypeCodes) {
                        maxWidth = Double.MAX_VALUE
                        cellFormat {
                            text = it.name
                        }
                    }
                }
                row {
                    label("DICTNAME:")
                    textfield(vmDetail.dictname)
                }
                row {
                    label("URL:")
                    textfield(vmDetail.url)
                }
                row {
                    label("CHCONV:")
                    textfield(vmDetail.chconv)
                }
                row {
                    label("SITEID:")
                    textfield(vmDetail.siteid)
                }
                row {
                    label("WAIT:")
                    textfield(vmDetail.wait)
                }
                row {
                    label("TRANSFORM:")
                }
                row {
                    textarea(vmDetail.transform) {
                        gridpaneConstraints {
                            columnSpan = 2
                        }
                    }
                }
            }
            vbox {
                label("AUTOMATION:")
                textarea(vmDetail.automation) {
                    vgrow = Priority.ALWAYS
                }
                label("TEMPLATE:")
                textarea(vmDetail.template) {
                    vgrow = Priority.ALWAYS
                }
                label("SITEID:")
                textarea(vmDetail.template2) {
                    vgrow = Priority.ALWAYS
                }
            }
        }
        buttonbar {
            button("Edit TRANSFORM", ButtonBar.ButtonData.LEFT).action {
                find<TransformEditView>("vm" to TransformEditViewModel(vmDetail)) { openModal(block = true) }
            }
            button("OK") {
                isDefaultButton = true
            }.action {
                result = true
                vmDetail.commit()
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
