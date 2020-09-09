package com.zwstudio.lolly.domain.misc

import com.zwstudio.lolly.data.SettingsViewModel
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty

enum class ReviewMode {
    ReviewAuto, Test, ReviewManual;

    override fun toString() = when(this) {
        ReviewAuto -> "Review(Auto)"
        Test -> "Test"
        ReviewManual -> "Review(Manual)"
    }
}

class MReviewOptions {
    val isEmbedded = false
    val modeItem = SimpleObjectProperty(SettingsViewModel.lstReviewModes[0])
    val mode get() = ReviewMode.values()[modeItem.value.value]
    val shuffled = SimpleBooleanProperty()
    val interval = SimpleIntegerProperty(5)
    val groupSelected = SimpleIntegerProperty(1)
    val groupCount = SimpleIntegerProperty(1)
}
