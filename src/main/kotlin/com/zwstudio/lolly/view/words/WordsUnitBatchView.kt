package com.zwstudio.lolly.view.words

import tornadofx.*

class WordsUnitBatchView : Fragment("Words in Unit Batch") {
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

