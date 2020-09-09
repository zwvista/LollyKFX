package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsReviewViewModel
import com.zwstudio.lolly.domain.misc.MReviewOptions
import com.zwstudio.lolly.view.ILollySettings
import com.zwstudio.lolly.view.misc.ReviewOptionsView
import tornadofx.*

class WordsReviewView : Fragment("Words Review"), ILollySettings {
    var vm = WordsReviewViewModel()

    override val root = vbox {
        tag = this@WordsReviewView
        toolbar {
            button("New Test").action {
                val modal = find<ReviewOptionsView>("vm" to MReviewOptions()) { openModal(block = true) }
                if (modal.result) {
                }
            }
            checkbox("Speak?")
            button("Speak")
        }
        gridpane {
            paddingAll = 10.0
            hgap = 10.0
            vgap = 10.0
        }
    }

    init {
        onSettingsChanged()
    }

    override fun onSettingsChanged() {
    }
}
