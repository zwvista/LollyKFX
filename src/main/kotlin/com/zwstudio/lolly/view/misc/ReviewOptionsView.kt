package com.zwstudio.lolly.view.misc

import com.zwstudio.lolly.data.SettingsViewModel
import com.zwstudio.lolly.domain.misc.MReviewOptions
import tornadofx.*

class ReviewOptionsView : Fragment("Review Options") {
    val vm : MReviewOptions by param()
    var result = false

    override val root = gridpane {
        tag = this@ReviewOptionsView
        paddingAll = 10.0
        hgap = 10.0
        vgap = 10.0
        row {
            label("Mode:")
            choicebox(vm.modeItem, SettingsViewModel.lstReviewModes) {  }
        }
        row {  }
        checkbox("Shuffled") {
            gridpaneConstraints {
                columnRowIndex(1, 1)
            }
        }
        row {
            label("Interval:")
            spinner(1, 10, property = vm.interval)
        }
        row {
            label("Group:")
            hbox(10.0) {
                gridpaneConstraints {
                    columnSpan = 2
                }
                spinner(1, 10, property = vm.groupSelected)
                label("out of:")
                spinner(1, 10, property = vm.groupCount)
            }
        }
    }
}
