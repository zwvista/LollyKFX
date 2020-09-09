package com.zwstudio.lolly.view.phrases

import com.zwstudio.lolly.data.misc.ReviewOptionsViewModel
import com.zwstudio.lolly.data.misc.applyIO
import com.zwstudio.lolly.data.phrases.PhrasesReviewViewModel
import com.zwstudio.lolly.view.ILollySettings
import com.zwstudio.lolly.view.misc.ReviewOptionsView
import io.reactivex.rxjava3.core.Observable
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import tornadofx.*
import java.util.concurrent.TimeUnit

class PhrasesReviewView : Fragment("Phrases Review"), ILollySettings {
    var vm = PhrasesReviewViewModel()

    override val root = vbox {
        tag = this@PhrasesReviewView
        toolbar {
            button("New Test").action {
                onSettingsChanged()
            }
            checkbox("Speak?", vm.isSpeaking)
            button("Speak")
        }
        gridpane {
            vgrow = Priority.ALWAYS
            paddingAll = 10.0
            hgap = 10.0
            vgap = 10.0
            constraintsForRow(0).percentHeight = 20.0
            constraintsForRow(1).percentHeight = 20.0
            constraintsForRow(2).percentHeight = 20.0
            constraintsForRow(3).percentHeight = 20.0
            constraintsForRow(4).percentHeight = 20.0
            constraintsForColumn(0).hgrow = Priority.ALWAYS
            style {
                fontSize = 24.px
            }
            row {
                hbox(10.0) {
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
                }
            }
            row {
                label(vm.phraseTargetString) {
                    visibleWhen(vm.phraseTargetIsVisible)
                    style {
                        textFill = c("orange")
                    }
                }
            }
            row {
                label(vm.translationString) {
                    style {
                        textFill = c("magenta")
                    }
                }
            }
            row {
                textfield(vm.phraseInputString) {
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
        Observable.timer(1, TimeUnit.SECONDS).applyIO().subscribe {
            onSettingsChanged()
        }
    }

    override fun onSettingsChanged() {
        val modal = find<ReviewOptionsView>("vm" to ReviewOptionsViewModel(vm.options)) { openModal(block = true) }
        if (modal.result)
            vm.newTest()
    }
}
