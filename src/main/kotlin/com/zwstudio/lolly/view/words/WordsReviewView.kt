package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.misc.ReviewOptionsViewModel
import com.zwstudio.lolly.data.words.WordsReviewViewModel
import com.zwstudio.lolly.view.ILollySettings
import com.zwstudio.lolly.view.misc.ReviewOptionsView
import javafx.scene.layout.Priority
import tornadofx.*

class WordsReviewView : Fragment("Words Review"), ILollySettings {
    var vm = WordsReviewViewModel()

    override val root = vbox {
        tag = this@WordsReviewView
        toolbar {
            button("New Test").action {
                val modal = find<ReviewOptionsView>("vm" to ReviewOptionsViewModel(vm.options)) { openModal(block = true) }
                if (modal.result) {
                }
            }
            checkbox("Speak?")
            button("Speak")
        }
        gridpane {
            vgrow = Priority.ALWAYS
            paddingAll = 10.0
            hgap = 10.0
            vgap = 10.0
            constraintsForRow(0).percentHeight = 12.5
            constraintsForRow(1).percentHeight = 12.5
            constraintsForRow(2).percentHeight = 50.0
            constraintsForRow(3).percentHeight = 12.5
            constraintsForRow(4).percentHeight = 12.5
            constraintsForColumn(0).hgrow = Priority.ALWAYS
            row {
                hbox {
                    label(vm.indexString) {
                        visibleWhen(vm.indexIsVisible)
                    }
                    stackpane {
                        label("Correct") {
                            visibleWhen(vm.correctIsVisible)
                        }
                        label("Incorrect") {
                            visibleWhen(vm.incorrectIsVisible)
                        }
                    }
                    label(vm.accuracyString) {
                        visibleWhen(vm.accuracyIsVisible)
                    }
                }
            }
            row {
                hbox {
                    label(vm.wordTargetString) {
                        visibleWhen(vm.wordTargetIsVisible)
                    }
                    label(vm.noteTargetString) {
                        visibleWhen(vm.noteTargetIsVisible)
                    }
                }
            }
            row {
                textarea(vm.translationString) {

                }
            }
            row {
                textfield(vm.wordInputString)
            }
            row {
                button(vm.checkString).action {
                    vm.check()
                }
            }
        }
    }

    init {
        onSettingsChanged()
    }

    override fun onSettingsChanged() {
    }
}
