package com.zwstudio.lolly.views.misc

import com.zwstudio.lolly.viewmodels.misc.ReviewOptionsViewModel
import com.zwstudio.lolly.viewmodels.misc.SettingsViewModel
import tornadofx.*

class ReviewOptionsView : Fragment("Review Options") {
    val vm: ReviewOptionsViewModel by param()
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
        row {
            checkbox("Speaking Enabled", vm.speakingEnabled)
            checkbox("Shuffled", vm.shuffled)
        }
        row {
            checkbox("On Repeat", vm.onRepeat)
            checkbox("Forward", vm.moveForward)
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
        row {
            label("Review Count:")
            spinner(10, 100, property = vm.reviewCount)
        }
        vbox(10.0) {
            gridpaneConstraints {
                columnRowIndex(3, 0)
            }
            button("OK") {
                isDefaultButton = true
            }.action {
                result = true
                vm.commit()
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
