package com.zwstudio.lolly.views.phrases

import com.zwstudio.lolly.viewmodels.misc.ReviewOptionsViewModel
import com.zwstudio.lolly.viewmodels.misc.applyIO
import com.zwstudio.lolly.viewmodels.phrases.PhrasesReviewViewModel
import com.zwstudio.lolly.views.ILollySettings
import com.zwstudio.lolly.views.misc.ReviewOptionsView
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
                        visibleWhen(vm.indexVisible)
                    }
                    stackpane {
                        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE)
                        label("Correct") {
                            visibleWhen(vm.correctVisible)
                            style {
                                textFill = c("green")
                            }
                        }
                        label("Incorrect") {
                            visibleWhen(vm.incorrectVisible)
                            style {
                                textFill = c("red")
                            }
                        }
                    }
                }
            }
            row {
                label(vm.phraseTargetString) {
                    visibleWhen(vm.phraseTargetVisible)
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
                textfield(vm.phraseInputString).action {
                    vm.check(true)
                }
            }
            row {
                hbox(spacing = 10) {
                    region {
                        hgrow = Priority.ALWAYS
                    }
                    checkbox("On Repeat", vm.onRepeat) {
                        visibleWhen { vm.onRepeatVisible }
                    }
                    checkbox("Forward", vm.moveForward) {
                        visibleWhen { vm.moveForwardVisible }
                    }
                    button(vm.checkPrevString) {
                        enableWhen { vm.checkPrevEnabled }
                        visibleWhen { vm.checkPrevVisible }
                    }.action {
                        vm.check(false)
                    }
                    button(vm.checkNextString) {
                        enableWhen { vm.checkNextEnabled }
                    }.action {
                        vm.check(true)
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
