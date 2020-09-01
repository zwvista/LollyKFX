package com.zwstudio.lolly.view.misc

import com.zwstudio.lolly.data.SettingsViewModel
import javafx.geometry.Insets
import tornadofx.*

class SettingsView : Fragment("Settings") {
    val vm : SettingsViewModel by inject()
    var result = false

    override val root = gridpane {
        padding = Insets(10.0)
        hgap = 10.0
        vgap = 10.0
        setStyle("-fx-background-color: #FF0000;");
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

