package com.zwstudio.lolly.domain.misc

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
    var mode = ReviewMode.ReviewAuto
    var shuffled = false
    var interval = 5
    var groupSelected = 1
    var groupCount = 1
}
