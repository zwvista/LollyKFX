package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.words.WordsUnitBatchViewModel
import tornadofx.*

class WordsUnitBatchView : Fragment("Words in Unit Batch Edit") {
    val vm : WordsUnitBatchViewModel by param()
    var result = false

    override val root = form {
        buttonbar {
            button("OK") {
                isDefaultButton = true
                action {
                    result = true
                    close()
                }
            }
            button("Cancel") {
                isCancelButton = true
                action {
                    close()
                }
            }
        }
    }
}

