package com.zwstudio.lolly.data.misc

import com.zwstudio.lolly.domain.misc.MReviewOptions
import com.zwstudio.lolly.domain.misc.ReviewMode
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*


class ReviewOptionsViewModel(item: MReviewOptions) : ItemViewModel<MReviewOptions>(item) {
    val modeItem = SimpleObjectProperty(SettingsViewModel.lstReviewModes[item.mode.ordinal])
    val shuffled = bind(MReviewOptions::shuffled)
    val interval = bind(MReviewOptions::interval)
    val groupSelected = bind(MReviewOptions::groupSelected)
    val groupCount = bind(MReviewOptions::groupCount)
    val speakingEnabled = bind(MReviewOptions::speakingEnabled)

    init {
        groupSelected.addListener { _, _, newValue ->
            if (newValue > groupCount.value)
                groupCount.value = newValue
        }
        groupCount.addListener { _, _, newValue ->
            if (newValue < groupSelected.value)
                groupSelected.value = newValue
        }
    }

    override fun onCommit() {
        super.onCommit()
        item.mode = ReviewMode.values()[modeItem.value.value]
    }
}
