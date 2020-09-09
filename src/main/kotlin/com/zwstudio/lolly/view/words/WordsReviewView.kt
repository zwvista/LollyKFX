package com.zwstudio.lolly.view.words

import com.zwstudio.lolly.data.misc.ReviewOptionsViewModel
import com.zwstudio.lolly.data.words.WordsReviewViewModel
import com.zwstudio.lolly.view.ILollySettings
import com.zwstudio.lolly.view.misc.ReviewOptionsView
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import tornadofx.*

class WordsReviewView : Fragment("Words Review"), ILollySettings {
    var vm = WordsReviewViewModel()

    override val root = vbox {
        tag = this@WordsReviewView
        toolbar {
            button("New Test").action {
                val modal = find<ReviewOptionsView>("vm" to ReviewOptionsViewModel(vm.options)) { openModal(block = true) }
                if (modal.result)
                    vm.newTest()
            }
            checkbox("Speak?", vm.isSpeaking)
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
                hbox(10.0) {
                    style {
                        fontSize = 24.px
                    }
                    label(vm.indexString) {
                        visibleWhen(vm.indexIsVisible)
                    }
                    stackpane {
                        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE)
                        label("Correct") {
                            visibleWhen(vm.correctIsVisible)
                            style {
                                textFill = c("green")
                            }
                        }
                        label("Incorrect") {
                            visibleWhen(vm.incorrectIsVisible)
                            style {
                                textFill = c("red")
                            }
                        }
                    }
                    region {
                        hgrow = Priority.ALWAYS
                    }
                    label(vm.accuracyString) {
                        visibleWhen(vm.accuracyIsVisible)
                    }
                }
            }
            row {
                hbox(10.0) {
                    style {
                        fontSize = 24.px
                    }
                    label(vm.wordTargetString) {
                        visibleWhen(vm.wordTargetIsVisible)
                        style {
                            textFill = c("orange")
                        }
                    }
                    label(vm.noteTargetString) {
                        visibleWhen(vm.noteTargetIsVisible)
                        style {
                            textFill = c("pink")
                        }
                    }
                }
            }
            row {
                textarea(vm.translationString) {
                    style {
                        fontSize = 12.px
                    }
                }
            }
            row {
                textfield(vm.wordInputString) {
                    style {
                        fontSize = 24.px
                    }
                    action {
                        vm.check()
                    }
                }
            }
            row {
                hbox {
                    region {
                        hgrow = Priority.ALWAYS
                    }
                    button(vm.checkString).action {
                        alignment = Pos.CENTER_RIGHT
                        vm.check()
                    }
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
